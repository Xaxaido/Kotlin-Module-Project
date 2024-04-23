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
    var archives = mutableListOf<Data.Archive>()
    val archive
        get() = if (archives.isNotEmpty()) archives[archiveId] else Data.Archive()
    val list
        get() = when (getCurrentScreen()) {
            ARCHIVE -> archives
            else -> archive.data
        }

    fun getMessage(name: String) = when (getCurrentScreen()) {
        ARCHIVE -> STR.Archive.text(name)
        else -> STR.Note.text(name)
    }

    fun back() { screens -= screens.last() }

    private fun getCurrentScreen() = screens.last { it < EXIT }
}