object Nav : Mutable<Int, Archive> {

    const val CREATE = 0
    const val EXIT = -1
    const val ARCHIVE = -2
    const val CREATE_ARCHIVE = -3
    const val OPEN_ARCHIVE = -4
    const val NOTE = -5
    const val CREATE_NOTE = -6
    const val OPEN_NOTE = -7
    const val ASTERISK_COUNT = 5
    var archiveId = 0
    var back = EXIT
    var noteId = -1
    var screens = listOf(ARCHIVE)
    var archives = listOf<Archive>()
    private var list = listOf<Data>()

    fun isOutOfRange(id: Int): Int? {

        screens.last { it < EXIT }.let {
            list = if (it == ARCHIVE) archives else archives[archiveId].data
        }

        return if (id > list.size + 1) { println(Data.OUT_OF_RANGE); null } else id
    }

    fun getCurrentScreen(isBack: Boolean) = if (screens.size == 1 && isBack) EXIT else screens.last()
    override fun addValue(newValue: Int) { screens = screens.toList() + newValue }
    override fun addArchive(newValue: Archive) { archives = archives.toList() + newValue }
    override fun removeLast() { screens = screens.subList(0, screens.size - 1) }
}