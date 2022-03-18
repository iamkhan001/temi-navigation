package sg.mirobotic.teminavigation.ui.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import sg.mirobotic.teminavigation.R
import sg.mirobotic.teminavigation.config.AlarmBroadcastReceiver
import sg.mirobotic.teminavigation.databinding.FragmentAlermAddBinding
import sg.mirobotic.teminavigation.databinding.FragmentSelectLocationBinding
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.adapters.LocationsAdapter
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.MyMessage
import java.util.*

class ScheduleTaskDialogFragment(private val itemClickListener: ItemClickListener<TemiTask>): DialogFragment() {


    private var safebinding: FragmentAlermAddBinding? = null
    private val binding get() = safebinding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentAlermAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    var location = ""
    var time = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onLocationSelectListener = object : ItemClickListener<String> {
            override fun onItemClick(obj: String) {
                location = obj
            }
        }

        binding.location.setOnClickListener {
            val locationSelectLocationDialogFragment = SelectLocationDialogFragment(onLocationSelectListener)
            locationSelectLocationDialogFragment.show(childFragmentManager, "SelectLocation")
        }

        var hour = 0
        var minute = 0

        binding.time.setOnClickListener {
            val curTime = Calendar.getInstance()
            val mTimePicker = TimePickerDialog(requireContext(),
                { _, hourOfDay, minuteOfHour ->
                    hour = hourOfDay
                    minute = minuteOfHour
                    time = String.format("%d:%d", hourOfDay, minuteOfHour)
                    binding.time.text = time
                },  curTime.get(Calendar.HOUR_OF_DAY), curTime.get(Calendar.MINUTE), false)
            mTimePicker.show()
        }

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        binding.save.setOnClickListener {
            if (location.isEmpty()) {
                MyMessage.showToast(context, "Select location")
                return@setOnClickListener
            }

            if (time.isEmpty()) {
                MyMessage.showToast(context, "Select Time")
                return@setOnClickListener
            }

            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                }.timeInMillis,
                PendingIntent.getBroadcast(
                    requireContext(),
                    0,
                    Intent(requireContext(), AlarmBroadcastReceiver::class.java).apply {
                        putExtra("time", time)
                        putExtra("location", location)
                    },
                    PendingIntent.FLAG_CANCEL_CURRENT
                )
            )

            itemClickListener.onItemClick(TemiTask(location, time))
        }

        binding.cancel.setOnClickListener { dismiss() }

    }

    override fun onResume() {
        super.onResume()
        val width = resources.getDimensionPixelSize(R.dimen.task_w)
        val height = resources.getDimensionPixelSize(R.dimen.task_h)
        dialog?.window?.setLayout(width, height)
    }
}

class TemiTask(val location: String, val time: String)