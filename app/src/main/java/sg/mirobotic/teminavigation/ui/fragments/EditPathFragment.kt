package sg.mirobotic.teminavigation.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.mirobotic.dialog.myDialog.SweetAlertDialog
import sg.mirobotic.teminavigation.data.Path
import sg.mirobotic.teminavigation.databinding.FragmentEditPathBinding
import sg.mirobotic.teminavigation.ui.activities.MainActivity.Companion.DIALOG_DELAY
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.adapters.LocationsAdapter
import sg.mirobotic.teminavigation.ui.adapters.SelectLocationAdapter
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel

class EditPathFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var alertDialog: SweetAlertDialog? = null
    private val handler = Handler(Looper.getMainLooper())

    private var safebinding: FragmentEditPathBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentEditPathBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mPath: Path = requireArguments().getSerializable("path") as Path? ?: return

        val path = mainViewModel.getPathDetails(mPath) ?: return

        binding.tvPathName.text = path.name

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

        selectedLocationAdapter.setData(path.locations)

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



        binding.btnSave.setOnClickListener {
            path.locations = selectedLocationAdapter.getList()
            mainViewModel.updatePath(path)
            selectedLocationAdapter.clearData()

            alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            alertDialog?.setTitle("Path updated!")
            alertDialog?.show()

            handler.postDelayed({
                alertDialog?.dismissWithAnimation()
            }, DIALOG_DELAY)

            activity?.onBackPressed()
        }

        mainViewModel.getSavedLocations()
    }


}