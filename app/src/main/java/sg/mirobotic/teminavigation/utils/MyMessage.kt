package sg.mirobotic.teminavigation.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.mirobotic.dialog.myDialog.SweetAlertDialog

class MyMessage {


    companion object {

        fun showToast(context: Context?, msg: String){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        fun showBar(view: View, msg: String) {
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
        }

        fun showBar(activity: Activity, msg: String) {
            Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
        }

        fun showError(context: Context, msg: String) {
            val alertDialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            alertDialog.titleText = "Error"
            alertDialog.contentText = msg
            alertDialog.confirmText = "OK"
            alertDialog.setConfirmClickListener { it.dismissWithAnimation() }
            alertDialog.show()
        }
    }

}