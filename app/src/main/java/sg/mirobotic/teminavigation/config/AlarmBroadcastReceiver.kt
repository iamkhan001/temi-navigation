package sg.mirobotic.teminavigation.config

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest

class AlarmBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val location = intent?.getStringExtra("location") ?: ""
        val time = intent?.getStringExtra("time") ?: ""

        Log.e("Alarm", "Location >> $location / $time")

        val robot = Robot.getInstance()

        val ttsRequest = TtsRequest.create("Going to $location", false)
        robot.speak(ttsRequest)
        robot.goTo(location)

    }
}