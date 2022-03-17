package sg.mirobotic.teminavigation.config

data class MessageEvent(val event: MyEvent, val action: String) {

    enum class MyEvent {
        GO_TO_LOCATION
    }
}
