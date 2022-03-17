package sg.mirobotic.teminavigation.config

import android.content.Context

import android.util.Base64
import android.util.Log
import com.robotemi.sdk.Robot
import java.util.*
import kotlin.random.Random

class UserDataProvider (context: Context) {

    companion object {
        private const val PREF = "user-data"

        private var ins: UserDataProvider? = null

        fun getInstance(context: Context): UserDataProvider? {

            if (ins == null) {
                ins = UserDataProvider(context)
            }

            return ins
        }

    }

    private val preferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun getDecodedPassword(): String {
        var password = preferences.getString("password", "")!!
        Log.e("Decode", "Before $password")
        val data: ByteArray = Base64.decode(password, Base64.DEFAULT)
        Log.d("Decode", "data $data")
        password = String(data)
        Log.e("Decode", "After $password")
        return password
    }

    private fun encode(password: String): String {
        return  Base64.encodeToString(password.toByteArray(), Base64.DEFAULT).toString()
    }

    fun getPassword(): String = preferences.getString("password", "5678")!!

    fun setAdminPassword(psw: String) {
        preferences.edit().apply {
            putString("password", psw)
            apply()
        }
    }


}