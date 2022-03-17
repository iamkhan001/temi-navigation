package sg.mirobotic.teminavigation.utils

interface OnRobotInteractionListener {

    fun gotToLocation(name: String)
    fun saveLocation(name: String)
    fun followStart()
    fun followStop()
    fun speak(msg: String)
    fun hideTopBar()
    fun showTopBar()
    fun goToPath(list: ArrayList<String>)

}