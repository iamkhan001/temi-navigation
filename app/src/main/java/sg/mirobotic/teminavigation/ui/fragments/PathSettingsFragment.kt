package sg.mirobotic.teminavigation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mirobotic.dialog.myDialog.SweetAlertDialog
import sg.mirobotic.teminavigation.data.Path
import sg.mirobotic.teminavigation.databinding.FragmentPathSettingsBinding
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.adapters.PathListAdapter
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.ScreenUtils

class PathSettingsFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var safebinding: FragmentPathSettingsBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentPathSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val onPathOptionSelectedListener = object : PathOptionsFragment.OnPathOptionSelectedListener {

        override fun onGotTo(path: Path) {
            val action = PathSettingsFragmentDirections.actionPathSettingsFragmentToPathNavigationFragment(path)
            findNavController().navigate(action)
        }

        override fun onEdit(path: Path) {
            val action = PathSettingsFragmentDirections.actionPathSettingsFragmentToEditPathFragment(path)
            findNavController().navigate(action)
        }

        override fun onDelete(path: Path) {
            val alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.NORMAL_TYPE)
            alertDialog.titleText = "Delete ${path.name}?"
            alertDialog.confirmText = "Yes"
            alertDialog.cancelText = "No"
            alertDialog.setConfirmClickListener {
                it.dismissWithAnimation()
                mainViewModel.deletePath(path)
                mainViewModel.loadPathList()
            }
            alertDialog.setCancelClickListener {
                it.dismissWithAnimation()
            }
            alertDialog.show()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onPathClickListener = object : ItemClickListener<Path> {
            override fun onItemClick(obj: Path) {
                PathOptionsFragment.show(childFragmentManager, obj,  onPathOptionSelectedListener)
            }
        }

        ScreenUtils.applyLayoutManager(binding.rvList)

        val pathListAdapter = PathListAdapter(onPathClickListener)
        binding.rvList.adapter = pathListAdapter

        mainViewModel.mPathList.observe(viewLifecycleOwner) {
            pathListAdapter.setData(it)
        }

        mainViewModel.loadPathList()

        binding.imgBack.setOnClickListener { activity?.onBackPressed() }
    }


}