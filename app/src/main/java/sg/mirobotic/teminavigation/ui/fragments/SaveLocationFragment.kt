package sg.mirobotic.teminavigation.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mirobotic.dialog.myDialog.SweetAlertDialog
import sg.mirobotic.teminavigation.R
import sg.mirobotic.teminavigation.databinding.FragmentSaveLocationBinding
import sg.mirobotic.teminavigation.ui.activities.MainActivity.Companion.isRobotFollowing
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.OnRobotInteractionListener

class SaveLocationFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var onRobotInteractionListener: OnRobotInteractionListener
    private var alertDialog: SweetAlertDialog? = null

    private val itemClickListener = object : ItemClickListener<String>{
        override fun onItemClick(obj: String) {

            alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
            alertDialog?.titleText = "Saving Location"
            alertDialog?.setCancelable(false)
            alertDialog?.show()

            onRobotInteractionListener.saveLocation(name = obj)

            alertDialog?.dismissWithAnimation()

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onRobotInteractionListener = context as OnRobotInteractionListener
    }

    private var safebinding: FragmentSaveLocationBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentSaveLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (isRobotFollowing) {
            followStart()
            binding.btnSave.isEnabled = false
        }else {
            followStop()
        }

        binding.btnFollow.setOnClickListener {
            if (!isRobotFollowing) {
                followStart()
                onRobotInteractionListener.followStart()
                binding.btnSave.isEnabled = false
                return@setOnClickListener
            }
            followStop()
            onRobotInteractionListener.followStop()
            binding.btnSave.isEnabled = true
        }

        binding.btnSave.setOnClickListener {
            SaveLocationDialogFragment.show(childFragmentManager, itemClickListener)
        }

        binding.imgBack.setOnClickListener { activity?.onBackPressed() }
    }

    private fun followStart() {
        binding.btnFollow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_stop, 0, 0)
        binding.btnFollow.text = getString(R.string.stop_following)
    }

    private fun followStop() {
        binding.btnFollow.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_cursor, 0, 0)
        binding.btnFollow.text = getString(R.string.follow_me)
    }

}