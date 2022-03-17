package sg.mirobotic.teminavigation.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import sg.mirobotic.teminavigation.databinding.FragmentLocationsBinding
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.adapters.LocationsAdapter
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.OnRobotInteractionListener
import sg.mirobotic.teminavigation.utils.ScreenUtils

class LocationsFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var onRobotInteractionListener: OnRobotInteractionListener
    private var isAdmin = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onRobotInteractionListener = context as OnRobotInteractionListener
    }

    private val itemClickListener = object : ItemClickListener<String>{
        override fun onItemClick(obj: String) {

            mainViewModel.isPathRequest = false
            onRobotInteractionListener.gotToLocation(obj)

        }
    }

    private var safebinding: FragmentLocationsBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val admin = arguments?.getBoolean("admin")
        if (admin != null) {
            isAdmin = admin
        }

        ScreenUtils.applyLayoutManager(binding.rvList)

        val adapter = LocationsAdapter(itemClickListener)
        binding.rvList.adapter = adapter

        mainViewModel.mLocations.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.imgBack.setOnClickListener { activity?.onBackPressed() }

    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getSavedLocations()

    }


}