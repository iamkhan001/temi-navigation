package sg.mirobotic.teminavigation.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robotemi.sdk.Robot
import com.tomergoldst.timekeeper.core.TimeKeeper
import com.tomergoldst.timekeeper.model.Alarm
import kotlinx.coroutines.launch
import sg.mirobotic.teminavigation.config.DataProcessor
import sg.mirobotic.teminavigation.config.UserDataProvider
import sg.mirobotic.teminavigation.data.Path

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    val mLocations = MutableLiveData<ArrayList<String>>()
    var isTopBarHidden = true

    var goingHome = false
    var isPathRequest = false

    val mPathList = MutableLiveData<ArrayList<Path>>()

    val mCurrentPath = MutableLiveData<ArrayList<String>>()
    val mCurrentPoint = MutableLiveData<Int>()

    lateinit var userDataProvider: UserDataProvider

    private var robot: Robot? = null
    private var dataProcessor: DataProcessor? = null

    fun init(context: Context, robot: Robot?) {
        this.robot = robot
        TimeKeeper.initialize(context)
        dataProcessor = DataProcessor(context)
        userDataProvider = UserDataProvider(context)
    }

    fun getSavedLocations() {

        viewModelScope.launch {

            if(robot == null) {
                Log.e(TAG, "getSavedLocations NULL")
                mLocations.postValue(getTestLocations())
                return@launch
            }

            robot?.let {

                Log.e(TAG, "getSavedLocations NOT NULL")
                if (it.locations.isEmpty()) {
                    mLocations.postValue(getTestLocations())
                }else {
                    val list = ArrayList<String>()
                    list.addAll(it.locations)
                    mLocations.postValue(list)
                }
            }
        }
    }

    private fun getTestLocations(): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 1..10) {
            list.add("Location $i")
        }
        return list
    }

    fun loadPathList() {
        viewModelScope.launch {
            val list = dataProcessor?.getPaths()
            mPathList.postValue(list)
        }
    }

    fun getPathDetails(path: Path): Path? {
        return dataProcessor?.getLocationsForPath(path)
    }

    fun savePath(path: Path) {
        viewModelScope.launch {
            dataProcessor?.savePath(path)
        }
    }

    fun updatePath(path: Path) {
        viewModelScope.launch {
            dataProcessor?.updatePath(path)
        }
    }

    fun deletePath(path: Path) {
        dataProcessor?.deletePath(path)
    }

    fun sendMessage(s: String, locationName: String) {


    }

    val alarms = MutableLiveData<List<Alarm>?>()

    fun loadAlarms() {
        alarms.postValue(TimeKeeper.getAlarms())
    }

    fun removeAlarm(alarm: Alarm) {
        TimeKeeper.cancelAlarm(alarm.id)
        loadAlarms()
    }

    fun clearAlarms(){
        TimeKeeper.clear()
        alarms.postValue(null)
    }

    fun scheduleAlarm(alarm: Alarm) {
        TimeKeeper.setAlarm(alarm)
        loadAlarms()
    }

}
