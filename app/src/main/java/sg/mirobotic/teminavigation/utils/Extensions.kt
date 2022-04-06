package sg.mirobotic.teminavigation.utils

import java.text.SimpleDateFormat
import java.util.*

fun getRandomString(length: Int) : String {
    val charset = "0123456789"
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}

fun Date.getTimeNow(): Long {
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val data = format.format(this)
    return format.parse(data)!!.time
}