object Nav : Mutable<Int> {

    const val EXIT = -1
    const val CREATE = 0
    const val ARCHIVE = -2
    const val CREATE_ARCHIVE = -3
    const val OPEN_ARCHIVE = -4
    const val NOTE = -5
    const val CREATE_NOTE = -6
    const val OPEN_NOTE = -7
    const val ASTERISK_COUNT = 5
    val archives: MutableList<Archive> = mutableListOf()
    var archiveId = 0
    var back = EXIT
    var noteId = -1
    var screens = listOf(ARCHIVE)
    private var list = listOf<Data>()

    fun isOutOfRange(id: Int): Int? {

        for (i in screens.size - 1 downTo 0) {
            list = when (screens[i]) {
                ARCHIVE -> archives
                NOTE -> archives[archiveId].data
                else -> emptyList()
            }
            if (list.isNotEmpty()) break
        }

        return if (id > list.size) { println(Data.OUT_OF_RANGE); null } else id
    }

    fun getCurrentScreen(isBack: Boolean) = if (screens.size == 1 && isBack) EXIT else screens[screens.size - 1]
    override fun add(newValue: Int) { screens = screens.toList() + newValue }
    override fun removeLast() { screens = screens.subList(0, screens.size - 1) }
}