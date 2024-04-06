object Nav : Mutable {

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
    var archives = listOf<Archive>()
    val archive: Archive
        get() = archives[archiveId]
    val lastArchive: Archive
        get() = archives[archives.lastIndex]

    fun isOutOfRange(id: Int): Boolean {

        val list = screens.last { it < EXIT }.let { if (it == ARCHIVE) archives else archive.data }
        return if (id > list.size + 1) { println(Data.OUT_OF_RANGE); false } else true
    }

    fun getCurrentScreen(isBack: Boolean) = if (screens.size == 1 && isBack) EXIT else screens.last()
    override fun removeLast(): Boolean { screens = screens.subList(0, screens.lastIndex); return true }
}