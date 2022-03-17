package sg.mirobotic.teminavigation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import sg.mirobotic.teminavigation.data.Path
import sg.mirobotic.teminavigation.databinding.FragmentPathListBinding
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.adapters.PathListAdapter
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.ScreenUtils

class PathListFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var safebinding: FragmentPathListBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentPathListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onPathClickListener = object : ItemClickListener<Path> {
            override fun onItemClick(obj: Path) {
                val action = PathListFragmentDirections.actionPathListFragmentToPathNavigationFragment(obj)
                findNavController().navigate(action)
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