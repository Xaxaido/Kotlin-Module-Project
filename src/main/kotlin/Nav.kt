object Nav {

    const val CREATE = 0
    const val ARCHIVE_LIST = -2
    const val CREATE_ARCHIVE = -3
    const val NOTE_LIST = -4
    const val CREATE_NOTE = -5
    const val OPEN_NOTE = -6
    var back = -1
    private var archiveId = 0
    var noteId = 0
    var screens = listOf(ARCHIVE_LIST)
    var archives = mutableListOf<Archive>()
    val archive
        get() = if (archives.isNotEmpty()) archives[archiveId] else Archive()
    val list
        get() = when (screens.last { it < CREATE }) {
            ARCHIVE_LIST -> archives
            else -> archive.data
        }

    fun getNotes(id: Int) = screens.last().let {
        screens += id
        when (it) {
            ARCHIVE_LIST -> { archiveId = id - 1; NOTE_LIST }
            else -> { noteId = id - 1; OPEN_NOTE }
        }
    }

    fun back() = run { screens -= screens.last(); screens.lastOrNull() }
    inline fun <reified T> getClass(list: MutableList<T>) = T::class.simpleName?.uppercase()
}