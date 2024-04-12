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
    val list
        get(): List<Data> {
            return when (screens.last { it < EXIT }) {
                ARCHIVE -> { text = Data.Archive.text; archives }
                else -> { text = Data.Note.text; archive.data }
            }
        }
    var text = mapOf<String, String>()

    init { list }
}