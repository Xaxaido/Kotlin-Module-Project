object Nav {

    const val CREATE = 0
    const val EXIT = -1
    const val ARCHIVE = -2
    const val CREATE_ARCHIVE = -3
    const val OPEN_ARCHIVE = -4
    const val NOTE = -5
    const val CREATE_NOTE = -6
    const val OPEN_NOTE = -7
    var back = EXIT
    var archiveId = 0
    var noteId = 0
    var screens = listOf(ARCHIVE)
    var archives = listOf<Data.Archive>()
    val archive
        get() = if (archives.isNotEmpty()) archives[archiveId] else Data.Archive()
    val list
        get() = get(listOf(archives, archive.data))
    val text
        get() = get(listOf(Data.Archive.text, Data.Note.text))

    private fun <T> get(list: List<T>) = when (screens.last { it < EXIT }) {
        ARCHIVE -> list[0]
        else -> list[1]
    }
}