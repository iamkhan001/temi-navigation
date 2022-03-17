package sg.mirobotic.teminavigation.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.robotemi.sdk.NlpResult
import com.robotemi.sdk.Robot
import com.robotemi.sdk.Robot.*
import com.robotemi.sdk.TtsRequest
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage
import com.robotemi.sdk.exception.OnSdkExceptionListener
import com.robotemi.sdk.exception.SdkException
import com.robotemi.sdk.face.ContactModel
import com.robotemi.sdk.face.OnFaceRecognizedListener
import com.robotemi.sdk.listeners.*
import com.robotemi.sdk.model.CallEventModel
import com.robotemi.sdk.model.DetectionData
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener
import com.robotemi.sdk.navigation.model.Position
import com.robotemi.sdk.permission.OnRequestPermissionResultListener
import com.robotemi.sdk.permission.Permission
import com.robotemi.sdk.sequence.OnSequencePlayStatusChangedListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import sg.mirobotic.teminavigation.R
import sg.mirobotic.teminavigation.config.MessageEvent
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.MyMessage
import sg.mirobotic.teminavigation.utils.OnRobotInteractionListener
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), OnRobotInteractionListener,
    NlpListener,
    OnRobotReadyListener,
    ConversationViewAttachesListener,
    WakeupWordListener,
    ActivityStreamPublishListener,
    TtsListener,
    OnBeWithMeStatusChangedListener,
    OnGoToLocationStatusChangedListener,
    OnLocationsUpdatedListener,
    OnConstraintBeWithStatusChangedListener,
    OnDetectionStateChangedListener,
    AsrListener,
    OnTelepresenceEventChangedListener,
    OnRequestPermissionResultListener,
    OnDistanceToLocationChangedListener,
    OnCurrentPositionChangedListener,
    OnSequencePlayStatusChangedListener,
    OnRobotLiftedListener,
    OnDetectionDataChangedListener,
    OnUserInteractionChangedListener,
    OnFaceRecognizedListener,
    OnSdkExceptionListener {


    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var context: Context
    private var robot: Robot? = null

    companion object {
        
        private const val TAG = "MainActivity"
        var isRobotFollowing = false

        const val HOME_LOCATION = "home base"
        private const val REQUEST_CODE_NORMAL = 100

        private const val DELAY = 3000L
        const val DIALOG_DELAY = 1500L

        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

    }

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        robot = Robot.getInstance()
        robot?.addOnRequestPermissionResultListener(this)
        robot?.addOnTelepresenceEventChangedListener(this)
        robot?.addOnFaceRecognizedListener(this)
        robot?.addOnSdkExceptionListener(this)
        
        mainViewModel.init(context,  robot)

        requestSettings()

    }

    var locationName = ""
    override fun gotToLocation(name: String) {
        Log.e(TAG, "gotToLocation $name")
        if (robot == null) {
            return
        }
        val goTo = name.toLowerCase(Locale.ENGLISH)
        for (location in robot!!.locations) {
            Log.d(TAG, "check $location > $goTo")

            if (location.toLowerCase(Locale.ENGLISH) == goTo) {
                robot?.goTo(location)
                speak("going to $name")
                locationName = name
                return
            }
        }
        speak("i don't know this location")
        
    }

    override fun saveLocation(name: String) {
        Log.e(TAG, "saveLocation $name")
        if (robot == null) {
            return
        }
        val result = robot!!.saveLocation(name)
        if (result) {
            robot?.speak(TtsRequest.create("Location Saved", true))
        } else {
            robot?.speak(TtsRequest.create("Failed to save location", true))
        }
    }

    override fun followStart() {
        isRobotFollowing = true
        robot?.beWithMe()
        speak("following")
        Log.e(TAG, "following")
    }

    override fun followStop() {
        isRobotFollowing = false
        robot?.stopMovement()
        speak("ok")
        Log.e(TAG, "follow stop")
    }

    override fun speak(msg: String) {
        val ttsRequest = TtsRequest.create(msg, false)
        robot?.speak(ttsRequest)
    }

    /* IMPORTANT events */

    override fun onWakeupWord(wakeupWord: String, direction: Int) {
        Log.e(TAG, "onWakeupWord $wakeupWord $direction")
    }

    override fun onBeWithMeStatusChanged(status: String) {
        Log.e(TAG, "onBeWithMeStatusChanged $status")
        /*
        when (status) {
            OnBeWithMeStatusChangedListener.ABORT -> robot?.speak(TtsRequest.create("Abort", false))
            OnBeWithMeStatusChangedListener.CALCULATING -> robot?.speak(TtsRequest.create("Calculating", false))
            OnBeWithMeStatusChangedListener.LOCK -> robot?.speak(TtsRequest.create("Lock", false))
            OnBeWithMeStatusChangedListener.SEARCH -> robot?.speak(TtsRequest.create("search", false))
            OnBeWithMeStatusChangedListener.START -> robot?.speak(TtsRequest.create("Start", false))
            OnBeWithMeStatusChangedListener.TRACK -> robot?.speak(TtsRequest.create("Track", false))
        }
         */
    }

    override fun onGoToLocationStatusChanged(
        location: String,
        status: String,
        descriptionId: Int,
        description: String
    ) {
        Log.e(TAG, "onGoToLocationStatusChanged $location $status")
//        robot!!.speak(TtsRequest.create(description, false))

        when (status) {
            OnGoToLocationStatusChangedListener.START -> {
//                robot!!.speak(TtsRequest.create("Starting", false))
            }
            OnGoToLocationStatusChangedListener.CALCULATING -> {
//                robot!!.speak(TtsRequest.create("Calculating", false))
            }
            OnGoToLocationStatusChangedListener.GOING -> {
//                robot!!.speak(TtsRequest.create("Going", false))
            }
            OnGoToLocationStatusChangedListener.COMPLETE -> {
//                robot!!.speak(TtsRequest.create("Completed", false))
                speak("reached on $locationName")
                if (!mainViewModel.goingHome) {
                    mainViewModel.sendMessage("reached", locationName)
                }
            }
            OnGoToLocationStatusChangedListener.ABORT -> {
//                robot!!.speak(TtsRequest.create("Cancelled", false))
            }
        }
    }

    override fun onNlpCompleted(nlpResult: NlpResult) {
        Log.e(TAG, "onNlpCompleted $nlpResult")
    }

    override fun onRobotReady(isReady: Boolean) {
        Log.e(TAG, "onRobotReady $isReady")

        if (isReady) {
            try {
                val activityInfo = packageManager.getActivityInfo(
                    componentName,
                    PackageManager.GET_META_DATA
                )
                // Robot.getInstance().onStart() method may change the visibility of top bar.
                robot?.onStart(activityInfo)

            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException(e)
            }
        }

    }

    override fun onLocationsUpdated(locations: List<String>) {
        Log.e(TAG, "onLocationsUpdated $locations")
    }

    override fun onConstraintBeWithStatusChanged(isConstraint: Boolean) {
        Log.e(TAG, "onConstraintBeWithStatusChanged $isConstraint")
    }

    override fun onDistanceToLocationChanged(distances: Map<String, Float>) {
        val keys = distances.keys
        for(k in keys) {
            Log.d(TAG, "onDistanceToLocationChanged: ${distances[k]}")
        }
    }

    /* other events */
    override fun onUserInteraction(isInteracting: Boolean) {
        Log.e(TAG, "onUserInteraction $isInteracting")
    }

    override fun onPublish(message: ActivityStreamPublishMessage) {
        Log.e(TAG, "onPublish ${message.success()}")
    }

    override fun onAsrResult(asrResult: String) {
        Log.e(TAG, "onAsrResult $asrResult")
    }

    override fun onConversationAttaches(isAttached: Boolean) {
        Log.e(TAG, "onConversationAttaches $isAttached")
    }

    override fun onTtsStatusChanged(ttsRequest: TtsRequest) {
        Log.e(TAG, "onTtsStatusChanged ${ttsRequest.speech}")
    }

    override fun onDetectionStateChanged(state: Int) {

    }

    override fun onTelepresenceEventChanged(callEventModel: CallEventModel) {

    }

    override fun onCurrentPositionChanged(position: Position) {
        Log.e(TAG, "onCurrentPositionChanged ${position.x} ${position.y} ${position.tiltAngle}")
    }

    override fun onSequencePlayStatusChanged(status: Int) {

    }

    override fun onRobotLifted(isLifted: Boolean, reason: String) {
        Log.e(TAG, "onRobotLifted $isLifted : $reason")
    }

    override fun onDetectionDataChanged(detectionData: DetectionData) {

    }

    override fun onFaceRecognized(contactModelList: List<ContactModel>) {
        Log.e(TAG, "onFaceRecognized ")
    }

    override fun onSdkError(sdkException: SdkException) {
        Log.e(TAG, "onSdkError ${sdkException.message}")
    }

    /**
     * Setting up all the event listeners
     */
    override fun onStart() {
        super.onStart()

        robot?.addOnRobotReadyListener(this)
        robot?.addNlpListener(this)
        robot?.addOnBeWithMeStatusChangedListener(this)
        robot?.addOnGoToLocationStatusChangedListener(this)
        robot?.addConversationViewAttachesListenerListener(this)
        robot?.addWakeupWordListener(this)
        robot?.addTtsListener(this)
        robot?.addOnLocationsUpdatedListener(this)
        robot?.addOnConstraintBeWithStatusChangedListener(this)
        robot?.addOnDetectionStateChangedListener(this)
        robot?.addAsrListener(this)
        robot?.addOnDistanceToLocationChangedListener(this)
        robot?.addOnCurrentPositionChangedListener(this)
        robot?.addOnSequencePlayStatusChangedListener(this)
        robot?.addOnRobotLiftedListener(this)
        robot?.addOnDetectionDataChangedListener(this)
        robot?.addOnUserInteractionChangedListener(this)
        robot?.hideTopBar()

        EventBus.getDefault().register(this)
    }

    /**
     * Removing the event listeners upon leaving the app.
     */
    override fun onStop() {
        super.onStop()
        robot?.removeOnRobotReadyListener(this)
        robot?.removeNlpListener(this)
        robot?.removeOnBeWithMeStatusChangedListener(this)
        robot?.removeOnGoToLocationStatusChangedListener(this)
        robot?.removeConversationViewAttachesListenerListener(this)
        robot?.removeWakeupWordListener(this)
        robot?.removeTtsListener(this)
        robot?.removeOnLocationsUpdateListener(this)
        robot?.removeOnDetectionStateChangedListener(this)
        robot?.removeAsrListener(this)
        robot?.removeOnDistanceToLocationChangedListener(this)
        robot?.removeOnCurrentPositionChangedListener(this)
        robot?.removeOnSequencePlayStatusChangedListener(this)
        robot?.removeOnRobotLiftedListener(this)
        robot?.removeOnDetectionDataChangedListener(this)
        robot?.addOnUserInteractionChangedListener(this)
        robot?.stopMovement()
        robot?.stopFaceRecognition()
        robot?.showTopBar()

        EventBus.getDefault().unregister(this)

    }

    override fun onDestroy() {
        robot?.removeOnRequestPermissionResultListener(this)
        robot?.removeOnTelepresenceEventChangedListener(this)
        robot?.removeOnFaceRecognizedListener(this)
        robot?.removeOnSdkExceptionListener(this)

        super.onDestroy()
    }

    override fun hideTopBar() {
        Log.e(TAG, "hideTopBar")
        robot?.hideTopBar()
        mainViewModel.isTopBarHidden = true
//        requestSettings()
    }

    override fun showTopBar() {
        Log.e(TAG, "showTopBar")
        robot?.showTopBar()
        mainViewModel.isTopBarHidden = false
    }

    private fun requestSettings() {

        if (robot == null) {
            return
        }

        if (robot!!.checkSelfPermission(Permission.SETTINGS) == Permission.GRANTED) {
            Toast.makeText(this, "You already had SETTINGS permission.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "already granted")
            return
        }
        val permissions: MutableList<Permission> = ArrayList()
        permissions.add(Permission.SETTINGS)
        robot!!.requestPermissions(permissions, REQUEST_CODE_NORMAL)
        Log.e(TAG, "Requesting  permission")


    }

    @SuppressLint("DefaultLocale")
    override fun onRequestPermissionResult(
        permission: Permission,
        grantResult: Int,
        requestCode: Int
    ) {

        val log = java.lang.String.format(
            "Permission: %s, grantResult: %d",
            permission.value,
            grantResult
        )

        Log.e(TAG, log)

        if (grantResult == Permission.DENIED) {
            MyMessage.showToast(context, "Permission Denied $REQUEST_CODE_NORMAL $requestCode")
            Log.e(TAG, "Permission Denied")
            return
        }
        Log.e(TAG, "Permission Granted $REQUEST_CODE_NORMAL $requestCode")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {

        Log.e(TAG, "onMessageEvent: ${event.event}")

        if (event.event == MessageEvent.MyEvent.GO_TO_LOCATION) {
            gotToLocation(event.action)
        }

    }

    override fun goToPath(list: ArrayList<String>) {

        mainViewModel.goingHome = false
        mainViewModel.isPathRequest = true

        mainViewModel.mCurrentPath.postValue(list)
        mainViewModel.mCurrentPoint.postValue(0)
        gotToLocation(list[0])
    }

}