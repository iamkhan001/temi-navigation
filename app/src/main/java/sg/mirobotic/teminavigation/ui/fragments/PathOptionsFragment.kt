package sg.mirobotic.teminavigation.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import sg.mirobotic.teminavigation.R
import sg.mirobotic.teminavigation.data.Path
import sg.mirobotic.teminavigation.databinding.FragmentPathOptionsBinding

class PathOptionsFragment(private val path: Path, private val onPathOptionSelectedListener: OnPathOptionSelectedListener) : DialogFragment() {

    companion object {
        fun show(fragmentManager: FragmentManager, path: Path, onPathOptionSelectedListener: OnPathOptionSelectedListener) {
            val locationOptionsFragment = PathOptionsFragment(path, onPathOptionSelectedListener)
            locationOptionsFragment.show(fragmentManager, "options")
        }
    }


    private var safebinding: FragmentPathOptionsBinding? = null
    private val binding get() = safebinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        safebinding = FragmentPathOptionsBinding.inflate(inflater, container, false)
        return binding.root    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = resources.getDimensionPixelSize(R.dimen.option_width)
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnCancel.setOnClickListener { dismiss() }

        binding.tvName.text = path.name

        binding.btnGo.setOnClickListener {
            onPathOptionSelectedListener.onGotTo(path)
            dismiss()
        }

        binding.btnEdit.setOnClickListener {
            onPathOptionSelectedListener.onEdit(path)
            dismiss()
        }

        binding.btnDelete.setOnClickListener {
            onPathOptionSelectedListener.onDelete(path)
            dismiss()
        }


    }

    interface OnPathOptionSelectedListener {

        fun onGotTo(path: Path)

        fun onEdit(path: Path)

        fun onDelete(path: Path)

    }

}