package sg.mirobotic.teminavigation.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import sg.mirobotic.teminavigation.data.Path
import sg.mirobotic.teminavigation.databinding.FragmentPathNavigationBinding
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.adapters.PathLocationsAdapter
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.OnRobotInteractionListener

class PathNavigationFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var safebinding: FragmentPathNavigationBinding? = null
    private val binding get() = safebinding!!

    private lateinit var onRobotInteractionListener: OnRobotInteractionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onRobotInteractionListener = context as OnRobotInteractionListener
    }

    private var pathAdapter: PathLocationsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentPathNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mPath: Path = requireArguments().getSerializable("path") as Path? ?: return

        val path = mainViewModel.getPathDetails(mPath) ?: return

        pathAdapter = PathLocationsAdapter(requireContext(), object : ItemClickListener<String> {
            override fun onItemClick(obj: String) {
            }
        })

        binding.rvList.adapter = pathAdapter
        pathAdapter?.setData(path.locations)

        mainViewModel.mCurrentPoint.observe(viewLifecycleOwner) {
            pathAdapter?.setCurrentPoint(it)
        }

        binding.imgBack.setOnClickListener {
            activity?.onBackPressed()
        }


        onRobotInteractionListener.goToPath(path.locations)

    }

}