import java.util.Scanner

class Menu {

    private val scanner = Scanner(System.`in`)

    private val createArchive: (String) -> Unit = {
        with (Nav) {
            archives += Data.Archive(it, listOf())
            archiveId = archives.lastIndex
            screens += NOTE
        }
    }

    private val createNote: (String) -> Unit = {
        val content = StringBuilder()

        println(Data.Note.STR_CREATE_NOTE)
        do {
            val isExit = getEntryInput().let { input ->
                if (input == "0") true else content.appendLine(input).isEmpty()
            }
        } while (!isExit)

        Nav.archives[Nav.archiveId].data += Data.Note(it, content.toString())
    }

    private val openNote: (Data.Note) -> Unit = {
        Decor.makeFrame(it)
        do {
            println("0${Nav.STR_EXIT}")
        } while (scanner.nextLine() != "0")
        Nav.screens -= Nav.screens.last()
        showMenu(Nav.archive.data, Nav.archive.name)
    }

    fun start() { showMenu(Nav.archives) }

    private fun addEntry(onAdd: (String) -> Unit) {
        println(Nav.text["EnterName"])
        onAdd(getEntryInput())
        showMenu(Nav.archive.data, Nav.archive.name)
    }

    private fun getEntryInput() = run {
        var input: String
        do {
            input = scanner.nextLine()
        } while (input.isEmpty().apply { if (this) println(Data.EMPTY_INPUT) })
        input
    }

    private fun showMenu(list: List<Data>, extra: String = "") {
        println(Decor.makeHeader(Nav.text["List"]!! + extra, Decor.getMaxWidth(list)))
        println((Nav.text["Create"]!!))
        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        Nav.back = (list.size + 1).apply { println("$this${Nav.STR_EXIT}") }
        getUserInput()
    }

    private fun draw(id: Int) {
        with(Nav) {
            when (id) {
                ARCHIVE -> showMenu(archives)
                CREATE_ARCHIVE -> addEntry(createArchive)
                OPEN_ARCHIVE, NOTE -> showMenu(archives.last().data, archives.last().name)
                CREATE_NOTE -> addEntry(createNote)
                OPEN_NOTE -> openNote(archive.data[noteId])
            }
        }
    }

    private fun getScreen(id: Int) = with (Nav) {
        val screen = screens.last()
        when {
            screen == back -> screens[screens.size - 2]
            id == CREATE -> if (screen == ARCHIVE) CREATE_ARCHIVE else CREATE_NOTE
            id > CREATE -> if (screen== NOTE) {
                    noteId = id - 1; OPEN_ARCHIVE
                } else {
                    noteId = id - 1; OPEN_NOTE
                }
            else -> screen
        }
    }

    private fun getUserInput() = with(Nav) {
        var id: Int?
        do {
            id = scanner.nextLine().toIntOrNull()

            val isCorrect = when (id) {
                null -> { println(Data.NOT_NUMBER); false }
                CREATE -> true
                back -> {
                    id = screens.last().let { if (it == ARCHIVE) EXIT else it }
                    screens -= screens.last(); true
                }
                in list.size + 1 until Int.MAX_VALUE,
                in Int.MIN_VALUE until CREATE -> { println(Data.OUT_OF_RANGE); false }
                else -> {
                    screens += if (screens.last() == ARCHIVE) NOTE else OPEN_NOTE
                    true
                }
            }
        } while (!isCorrect)

        if (id == EXIT) return else draw(getScreen(id!!))
    }
}