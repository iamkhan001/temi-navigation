package sg.mirobotic.teminavigation.utils

fun getRandomString(length: Int) : String {
    val charset = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}