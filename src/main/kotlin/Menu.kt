class Menu {

    private val input = Input()

    private val createNote: () -> Any = {
        val content = StringBuilder()
        println(Data.Note.STR_CREATE_NOTE)
        do {
            val input = input.getUserInput()
        } while ((input != "0").apply { if (this) content.appendLine(input) })
        content.toString()
    }

    private inline fun <reified T> add(list: MutableList<T>, onAdd: () -> Any = {}) = with (Nav) {
        println(text["EnterName"])
        val name = input.getUserInput()
        list.add(T::class.java.constructors.last().newInstance(name, onAdd()) as T)
        showMenu(archive.data, archive.name)
    }

    fun start() { draw(Nav.ARCHIVE) }

    private fun openNote(note: Data.Note) = with (Nav) {
        Decor.makeFrame(note)
        do {
            println("0${STR_EXIT}")
        } while (readln() != "0")
        back()
        showMenu(archive.data, archive.name)
    }

    private fun showMenu(list: List<Data>, extra: String = ""): Unit = with (Nav) {
        Decor.makeHeader(text["List"]!! + extra)
        println(text["Create"]!!)
        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        back = (list.size + 1).apply { println("$this$STR_EXIT") }
        input.getMenuInput().let { id ->
            if (id == EXIT) return else draw(getScreen(id))
        }
    }

    private fun draw(screen: Int) = with (Nav) {
        when (screen) {
            ARCHIVE -> showMenu(archives)
            CREATE_ARCHIVE -> add(archives) { screens += NOTE; mutableListOf<Data.Note>() }
            OPEN_ARCHIVE, NOTE -> showMenu(archive.data, archive.name)
            CREATE_NOTE -> add(archive.data, createNote)
            OPEN_NOTE -> openNote(archive.data[noteId])
        }
    }

    private fun getScreen(id: Int) = with (Nav) {
        val screen = screens.last()
        when {
            screen == back -> screens[screens.size - 2]
            id == CREATE -> if (screen == ARCHIVE) CREATE_ARCHIVE else CREATE_NOTE
            id > CREATE && screen == NOTE -> {
                archiveId = id - 1
                OPEN_ARCHIVE
            }
            else -> {
                if (id > CREATE && screen == OPEN_NOTE) noteId = id - 1
                screen
            }
        }
    }
}