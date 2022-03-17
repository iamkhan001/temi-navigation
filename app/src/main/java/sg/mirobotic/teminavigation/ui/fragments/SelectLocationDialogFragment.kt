package sg.mirobotic.teminavigation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import sg.mirobotic.teminavigation.databinding.FragmentSelectLocationBinding
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.adapters.LocationsAdapter
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel

class SelectLocationDialogFragment(private val itemClickListener: ItemClickListener<String>): DialogFragment() {


    private var safebinding: FragmentSelectLocationBinding? = null
    private val binding get() = safebinding!!
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentSelectLocationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clickListener = object : ItemClickListener<String> {
            override fun onItemClick(obj: String) {
                itemClickListener.onItemClick(obj)
                dismiss()
            }
        }

        val adapter = LocationsAdapter(clickListener)
        binding.rvList.adapter = adapter

        mainViewModel.mLocations.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.imgClose.setOnClickListener { dismiss() }

    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        val width = resources.getDimensionPixelSize(R.dimen.dialog_width)
//        val height = resources.getDimensionPixelSize(R.dimen.dialog_height)
//        dialog?.window?.setLayout(width, height)

        mainViewModel.getSavedLocations()
    }
}