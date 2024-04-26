object Nav {

    const val CREATE = 0
    const val EXIT = -1
    const val ARCHIVE_LIST = -2
    const val CREATE_ARCHIVE = -3
    const val NOTE_LIST = -4
    const val CREATE_NOTE = -5
    const val OPEN_NOTE = -6
    var back = EXIT
    private var archiveId = 0
    var noteId = 0
    var screens = listOf(ARCHIVE_LIST)
    var archives = mutableListOf<Archive>()
    val archive
        get() = if (archives.isNotEmpty()) archives[archiveId] else Archive()
    val list
        get() = when (screens.last { it < EXIT }) {
            ARCHIVE_LIST -> archives
            else -> archive.data
        }

    fun setId(id: Int) {
        if (screens.last() == ARCHIVE_LIST) archiveId = id - 1 else noteId = id - 1
        screens += id
    }

    fun back() { screens -= screens.last() }
    inline fun <reified T> getClass(list: MutableList<T>) = T::class.simpleName?.uppercase()
}