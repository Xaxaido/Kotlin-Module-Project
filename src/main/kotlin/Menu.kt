class Menu {

    private val input = Input()

    private val createNote: (String) -> Unit = {
        val content = StringBuilder()
        println(Data.Note.STR_CREATE_NOTE)
        do {
            val input = input.getUserInput()
        } while (input != "0".apply { content.appendLine(input) })
        Nav.archives[Nav.archiveId].data += Data.Note(it, content.toString())
    }

    fun start() { draw(Nav.ARCHIVE) }

    private fun addEntry(onAdd: (String) -> Unit) {
        println(Nav.text["EnterName"])
        onAdd(input.getUserInput())
        showMenu(Nav.archive.data, Nav.archive.name)
    }

    private fun openNote(note: Data.Note) = with(Nav) {
        Decor.makeFrame(note)
        do {
            println("0${STR_EXIT}")
        } while (input.scanner.nextLine() != "0")
        screens -= screens.last()
        showMenu(archive.data, archive.name)
    }

    private fun showMenu(list: List<Data>, extra: String = "") {
        Decor.makeHeader(Nav.text["List"]!! + extra)
        println(Nav.text["Create"]!!)
        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        Nav.back = (list.size + 1).apply { println("$this${Nav.STR_EXIT}") }
        getMenuId()
    }

    private fun draw(id: Int) = with(Nav) {
        when (id) {
            ARCHIVE -> showMenu(archives)
            CREATE_ARCHIVE -> addEntry { archives += Data.Archive(it, listOf()) }
            OPEN_ARCHIVE, NOTE -> showMenu(archives.last().data, archives.last().name)
            CREATE_NOTE -> addEntry(createNote)
            OPEN_NOTE -> openNote(archive.data[noteId])
        }
    }

    private fun getScreen(id: Int) = with (Nav) {
        val screen = screens.last()
        when {
            screen == back -> screens[screens.size - 2]
            id == CREATE -> if (screen == ARCHIVE) CREATE_ARCHIVE else CREATE_NOTE
            id > CREATE -> {
                noteId = id - 1
                if (screen == NOTE) OPEN_ARCHIVE else OPEN_NOTE
            }
            else -> screen
        }
    }

    private fun getMenuId() = input.getMenuInput().let {
        if (it == Nav.EXIT) return else draw(getScreen(it!!))
    }
}