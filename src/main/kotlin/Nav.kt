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
    var noteId = -1
    var screens = listOf(ARCHIVE)
    var archives = emptyList<Data.Archive>()
    val archive: Data.Archive
        get() = if (archives.isNotEmpty()) archives[archiveId] else Data.Archive()
    val text
        @Suppress("UNCHECKED_CAST")
        get() = get(Data.Archive.text, Data.Note.text) as Map<String, String>
    val list
        @Suppress("UNCHECKED_CAST")
        get() = get(archives, archive.data) as List<Data>

    private fun get(val1: Any, val2: Any) = when (screens.last { it < EXIT }) {
        ARCHIVE -> val1
        else -> val2
    }
}