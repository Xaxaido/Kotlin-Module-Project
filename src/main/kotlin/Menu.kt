class Menu {

    private val input = Input()

    private val createNote: (String) -> Unit = {
        val content = StringBuilder()
        println(Data.Note.STR_CREATE_NOTE)
        do {
            val input = input.getUserInput()
        } while ((input != "0").apply { if (this) content.appendLine(input) })
        Nav.archive.data += Data.Note(it, content.toString())
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

    private fun addEntry(onAdd: (String) -> Unit) = with (Nav) {
        println(text["EnterName"])
        onAdd(input.getUserInput())
        showMenu(archive.data, archive.name)
    }

    private fun showMenu(list: List<Data>, extra: String = ""): Unit = with (Nav) {
        Decor.makeHeader(text["List"]!! + extra)
        println(text["Create"]!!)
        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        back = (list.size + 1).apply { println("$this$STR_EXIT") }
        getMenuId()
    }

    private fun draw(screen: Int) = with (Nav) {
        when (screen) {
            ARCHIVE -> showMenu(archives)
            CREATE_ARCHIVE -> addEntry { archives += Data.Archive(it, listOf()) }
            OPEN_ARCHIVE, NOTE -> showMenu(archive.data, archive.name)
            CREATE_NOTE -> addEntry(createNote)
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

    private fun getMenuId() = input.getMenuInput().let { id ->
        if (id == Nav.EXIT) return else draw(getScreen(id))
    }
}