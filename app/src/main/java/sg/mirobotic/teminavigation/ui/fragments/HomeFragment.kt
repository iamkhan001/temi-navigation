package sg.mirobotic.teminavigation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import sg.mirobotic.teminavigation.R
import sg.mirobotic.teminavigation.data.TextIcon
import sg.mirobotic.teminavigation.databinding.FragmentHomeBinding
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.OnRobotInteractionListener

class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var onRobotInteractionListener: OnRobotInteractionListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onRobotInteractionListener = context as OnRobotInteractionListener
    }

    private var safebinding: FragmentHomeBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        val list = ArrayList<TextIcon>()

        list.add(TextIcon(R.drawable.ic_compass, getString(R.string.locations)))
        list.add(TextIcon(R.drawable.ic_map_2, getString(R.string.path)))

        Log.e("settings", "list ${list.size}")

//
//        val adapter = HomeAdapter(list, object : ItemClickListener<TextIcon> {
//            override fun onItemClick(obj: TextIcon) {
//                navigate(obj)
//            }
//        })


        binding.viewLocations.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_locationsFragment)
        }

        binding.viewPath.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_pathListFragment)
        }

        binding.imgBar.setOnClickListener {
            if (mainViewModel.isTopBarHidden) {
                onRobotInteractionListener.showTopBar()
                return@setOnClickListener
            }
            onRobotInteractionListener.hideTopBar()
        }

        binding.imgSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        binding.tvTitle.text = getString(R.string.app_name)

        onRobotInteractionListener.hideTopBar()


        Log.e("main", "Hide top bar")
    }

    private fun navigate(obj: TextIcon) {

        when(obj.name) {
            getString(R.string.locations)-> {
                findNavController().navigate(R.id.action_homeFragment_to_locationsFragment)
            }
            getString(R.string.path)-> {
                findNavController().navigate(R.id.action_homeFragment_to_pathListFragment)
            }
        }

    }

}