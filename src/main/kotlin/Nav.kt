object Nav {

    const val CREATE = 0
    const val EXIT = -1
    const val ARCHIVE = -2
    const val CREATE_ARCHIVE = -3
    const val OPEN_ARCHIVE = -4
    const val NOTE = -5
    const val CREATE_NOTE = -6
    const val OPEN_NOTE = -7
    var archiveId = 0
    var back = EXIT
    var noteId = -1
    var screens = listOf(ARCHIVE)
    var archives = listOf<Data.Archive>()
    val archive: Data.Archive
        get() = archives[archiveId]
    val lastArchive: Data.Archive
        get() = archives[archives.lastIndex]
    private val list
        @Suppress("UNCHECKED_CAST")
        get() = get("list") as List<Data>
    val text
        @Suppress("UNCHECKED_CAST")
        get() = get("text") as Map<String, String>

    private fun get(what: String): Any {
        return when (screens.last { it < EXIT }) {
            ARCHIVE -> if (what == "list") archives else Data.Archive.text
            else -> if (what == "list") archive.data else Data.Note.text
        }
    }

    fun add(value: Any) {
        when (value) {
            is Data.Archive -> archives = archives.toList() + value
            else -> screens = screens.toList() + value as Int
        }
    }

    fun removeLast() { screens = screens.subList(0, screens.lastIndex) }

    fun isOutOfRange(id: Int): Boolean = !(id > list.size + 1).apply {
        if (this) println(Data.OUT_OF_RANGE)
    }

    fun getCurrentScreen(isBack: Boolean) = if (screens.size == 1 && isBack) EXIT else screens.last()
}