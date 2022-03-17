package sg.mirobotic.teminavigation.data

import java.io.Serializable

data class Path(val name: String): Serializable {

    var id: Long = 0
    var locations = ArrayList<String>()

    constructor(id: Long, name: String): this(name) {
        this.id = id
    }

}