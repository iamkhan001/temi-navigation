package sg.mirobotic.teminavigation.config

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest
import com.tomergoldst.timekeeper.model.Alarm

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
//        val location = intent?.getStringExtra("location") ?: ""
//        val time = intent?.getStringExtra("time") ?: ""
//
//        Log.e("Alarm", "Location >> $location / $time")

        /*
          Extract alarms from intent
         */

        val alarms: List<Alarm>? = intent?.getParcelableArrayListExtra<Alarm>("alarms") as List<Alarm>?

        Log.e("Alarm", "Alarm List >> ${alarms?.size}")

        alarms?.let {
            if (it.isEmpty()){
                Log.e("Alarm", "Alarms List IS EMPTY >> ${it.size}")
                return@let
            }
            goToLocation(it[0])
        }

    }

    private fun goToLocation(alarm: Alarm) {

        Log.e("Alarm", "goToLocation >> ${alarm.payload} / ${alarm.id} / ${alarm.time}")

        val robot = Robot.getInstance()
        val location = alarm.payload
        val ttsRequest = TtsRequest.create("Going to $location", false)
        robot.speak(ttsRequest)
        robot.goTo(location)
    }
}