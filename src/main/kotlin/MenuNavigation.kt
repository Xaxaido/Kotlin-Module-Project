data class MenuNavigation(val initScreen: Int) : Mutable<Int> {

    var archiveId: Int = -1
    var noteId: Int = -1
    var screens = listOf(initScreen)
    var list = listOf<Data>()

    fun isOutOfRange(id: Int): Int? {
        return if (id > list.size) {
            println("Элемента с таким номером не существует")
            null
        } else id
    }

    fun getCurrentScreen(isBack: Boolean) = if (screens.size == 1 && isBack) Menu.EXIT else screens[screens.size - 1]

    override fun add(newValue: Int) { screens = copy().screens + newValue }

    override fun removeLast() { screens = copy().screens.subList(0, screens.size - 1) }
}