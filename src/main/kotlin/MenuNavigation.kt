data class MenuNavigation(val initScreen: Int) : Mutable<Int> {

    var archiveId: Int = -1
    var noteId: Int = -1
    var screens = listOf(initScreen)

    override fun add(newValue: Int) { this.screens = this.copy().screens + newValue }

    override fun removeLast() { this.screens = this.copy().screens.subList(0, this.screens.size - 1) }
}