package sg.mirobotic.teminavigation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import sg.mirobotic.teminavigation.R
import sg.mirobotic.teminavigation.config.UserDataProvider
import sg.mirobotic.teminavigation.data.TextIcon
import sg.mirobotic.teminavigation.databinding.FragmentSettingsBinding
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener
import sg.mirobotic.teminavigation.ui.adapters.SettingAdapter
import sg.mirobotic.teminavigation.ui.viewModels.MainViewModel
import sg.mirobotic.teminavigation.utils.MyMessage

class SettingsFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private var isLoggedIn = false

    private var safebinding: FragmentSettingsBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        safebinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = ArrayList<TextIcon>()

        list.add(TextIcon(R.drawable.ic_map_2, getString(R.string.saved_path)))
        list.add(TextIcon(R.drawable.ic_marker, getString(R.string.add_location)))
        list.add(TextIcon(R.drawable.ic_map, getString(R.string.create_path)))
        list.add(TextIcon(R.drawable.ic_ai, getString(R.string.schedule_location)))

        Log.e("settings", "list ${list.size}")

        val adapter = SettingAdapter(list, object : ItemClickListener<TextIcon> {
            override fun onItemClick(obj: TextIcon) {
                navigate(obj)
            }
        })

        binding.rvList.adapter = adapter

        binding.imgBack.setOnClickListener { activity?.onBackPressed() }

        if (isLoggedIn) {
            binding.viewLock.visibility = View.GONE
            binding.rvList.visibility = View.VISIBLE
        }

        val admin = UserDataProvider(requireContext()).getDecodedPassword()

        binding.btnLogin.setOnClickListener {
            val password = binding.etPassword.text.toString().trim()

            if (password == admin) {
                isLoggedIn = true
                binding.viewLock.visibility = View.GONE
                binding.rvList.visibility = View.VISIBLE
                binding.textPassword.error = ""
                return@setOnClickListener
            }

            binding.textPassword.error = "Wrong Password"

        }

    }



    private fun navigate(obj: TextIcon) {

        when(obj.name) {
            getString(R.string.add_location)-> {
                findNavController().navigate(R.id.action_settingsFragment_to_saveLocationFragment)
            }
            getString(R.string.saved_path) -> {
                findNavController().navigate(R.id.action_settingsFragment_to_pathSettingsFragment)
            }
            getString(R.string.create_path) -> {
                findNavController().navigate(R.id.action_settingsFragment_to_createPathFragment)
            }
            getString(R.string.schedule_location) -> {

            }
        }

    }

}