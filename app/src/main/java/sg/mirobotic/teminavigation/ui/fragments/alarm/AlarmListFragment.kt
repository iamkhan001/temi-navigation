package sg.mirobotic.teminavigation.ui.fragments.alarm

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.tomergoldst.timekeeper.model.Alarm
import sg.mirobotic.teminavigation.databinding.FragmentAlarmsBinding
import sg.mirobotic.teminavigation.databinding.FragmentLocationsBinding
import sg.mirobotic.teminavigation.ui.adapters.AlarmsListAdapter
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.fragments.ScheduleTaskDialogFragment
import sg.mirobotic.teminavigation.ui.fragments.TemiTask
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.MyMessage
import sg.mirobotic.teminavigation.utils.OnRobotInteractionListener

class AlarmListFragment: Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var onRobotInteractionListener: OnRobotInteractionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onRobotInteractionListener = context as OnRobotInteractionListener
    }

    private var safebinding: FragmentAlarmsBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentAlarmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        val onScheduleTaskListener = object : ItemClickListener<TemiTask> {
            override fun onItemClick(obj: TemiTask) {
                MyMessage.showToast(context, "${obj.location} - ${obj.time}")
                mainViewModel.loadAlarms()
            }
        }

        binding.btnAddNew.setOnClickListener {
            val scheduleTaskDialogFragment = ScheduleTaskDialogFragment(onScheduleTaskListener)
            scheduleTaskDialogFragment.show(childFragmentManager, "Task")
        }

        binding.btnRefresh.setOnClickListener {
            mainViewModel.loadAlarms()
        }

        binding.btnRemove.setOnClickListener {
            mainViewModel.clearAlarms()
        }

        val onAlarmOptionSelectListener = object : AlarmsListAdapter.OnAlarmOptionSelectListener {
            override fun onDelete(alarm: Alarm) {
                mainViewModel.removeAlarm(alarm)
            }
        }

        val alarmsListAdapter = AlarmsListAdapter(onAlarmOptionSelectListener)

        mainViewModel.alarms.observe(viewLifecycleOwner){
            if (it.isNullOrEmpty()) {
                alarmsListAdapter.clearData()
                return@observe
            }
            alarmsListAdapter.setData(it)
        }

    }


}