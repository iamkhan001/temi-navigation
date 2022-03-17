package sg.mirobotic.teminavigation.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import sg.mirobotic.teminavigation.R
import sg.mirobotic.teminavigation.databinding.FragmentDialogSaveLocationBinding
import sg.mirobotic.teminavigation.ui.adapters.ItemClickListener

class SaveLocationDialogFragment(private val itemClickListener: ItemClickListener<String>): DialogFragment() {

    private var safebinding: FragmentDialogSaveLocationBinding? = null
    private val binding get() = safebinding!!

    companion object {

        fun show(fragmentManager: FragmentManager, itemClickListener: ItemClickListener<String>) {
            val saveLocationDialogFragment = SaveLocationDialogFragment(itemClickListener)
            saveLocationDialogFragment.show(fragmentManager, "SaveLocation")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        safebinding = FragmentDialogSaveLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
//        dialog?.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val width = resources.getDimensionPixelSize(R.dimen.create_table_window)
//        val height = resources.getDimensionPixelSize(R.dimen.dialog_height)
        dialog?.window?.setLayout(width, ViewGroup.MarginLayoutParams.WRAP_CONTENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.setOnClickListener { dismiss() }

        binding.btnSave.setOnClickListener {

            val name = binding.etName.text.toString().trim()
            if (name.isEmpty()) {
                binding.textName.error = "Enter Name"
                return@setOnClickListener
            }
            binding.textName.error = ""

            itemClickListener.onItemClick(name)
            dismiss()
        }

    }

}