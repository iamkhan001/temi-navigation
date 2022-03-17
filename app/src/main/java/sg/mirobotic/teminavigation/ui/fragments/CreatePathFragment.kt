package sg.mirobotic.teminavigation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import sg.mirobotic.teminavigation.data.Path
import sg.mirobotic.teminavigation.databinding.FragmentCreatePathBinding
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.adapters.LocationsAdapter
import sg.mirobotic.teminavigation.ui.adapters.SelectLocationAdapter
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.MyMessage

class CreatePathFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var safebinding: FragmentCreatePathBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentCreatePathBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val onSelectedLocationClickListener = object : SelectLocationAdapter.OnLocationUpdateListener {
            override fun onAdd(list: ArrayList<String>) {
                if (binding.btnSave.visibility != View.VISIBLE) {
                    binding.btnSave.visibility = View.VISIBLE
                }
            }

            override fun onRemoved(list: ArrayList<String>) {
                if (list.size == 0) {
                    if (binding.btnSave.visibility == View.VISIBLE) {
                        binding.btnSave.visibility = View.GONE
                    }
                }
            }
        }

        val selectedLocationAdapter = SelectLocationAdapter(onSelectedLocationClickListener)

        val itemClickListener = object : ItemClickListener<String> {
            override fun onItemClick(obj: String) {
                selectedLocationAdapter.addLocation(obj)
            }
        }

        binding.rvSelectedLocations.adapter = selectedLocationAdapter

        val adapter = LocationsAdapter(itemClickListener)
        binding.rvLocations.adapter = adapter

        mainViewModel.mLocations.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.imgBack.setOnClickListener { activity?.onBackPressed() }

        val onPathNameEnterListener = object : ItemClickListener<String> {
            override fun onItemClick(obj: String) {
                val path = Path(obj)
                path.locations = selectedLocationAdapter.getList()
                mainViewModel.savePath(path)
                selectedLocationAdapter.clearData()
                MyMessage.showBar(view,"Path $obj saved!")
            }
        }

        binding.btnSave.setOnClickListener {
            SavePathDialogFragment.show(childFragmentManager, onPathNameEnterListener)
        }

        mainViewModel.getSavedLocations()
    }


}