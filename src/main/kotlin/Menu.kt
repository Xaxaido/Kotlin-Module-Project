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

        println("Введите текст заметки\n0. Сохранить и выйти")
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
            println("0. Выход")
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

    private fun getEntryInput(): String {
        var input: String

        do {
            input = scanner.nextLine()
        } while (input.isEmpty().apply {
            if (this) println("Поле не может быть пустым")
        })

        return input
    }

    private fun showMenu(list: List<Data>, extraText: String = "") {
        println(buildString {
            appendLine(
                Decor.makeHeader(Nav.text["List"]!! + extraText, Decor.getMaxWidth(list))
            )
            append(Nav.text["Create"]!!)
        })

        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        Nav.back = (list.size + 1).apply { println("$this. Выход") }
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

    private fun getScreen(id: Int): Int {
        with (Nav) {
            return screens.last().let {
                when {
                    it == back -> screens[screens.size - 2]
                    id == CREATE -> if (it == ARCHIVE) CREATE_ARCHIVE else CREATE_NOTE
                    id > CREATE && it == NOTE -> {
                        noteId = id - 1
                        OPEN_ARCHIVE
                    }
                    id > CREATE && it == OPEN_NOTE -> {
                        noteId = id - 1
                        OPEN_NOTE
                    }
                    else -> it
                }
            }
        }
    }

    private fun getUserInput() {
        var id: Int?

        with(Nav) {
            do {
                id = scanner.nextLine().toIntOrNull()

                val isCorrect = when (id) {
                    null -> { println("Введите число"); false }
                    in Int.MIN_VALUE until CREATE -> { println(Data.OUT_OF_RANGE); false }
                    CREATE -> true
                    back -> {
                        id = screens.last().let {
                            if (it == ARCHIVE) EXIT else it
                        }
                        screens -= screens.last()
                        true
                    }
                    else -> {
                        if (id!! > list.size + 1) {
                            println(Data.OUT_OF_RANGE); false
                        } else {
                            screens.last().let { screen ->
                                screens += when {
                                    id!! > CREATE -> if (screen == ARCHIVE) NOTE else OPEN_NOTE
                                    else -> screen
                                }
                            }
                            true
                        }
                    }
                }
            } while (!isCorrect)

            if (id == EXIT) return else draw(getScreen(id!!))
        }
    }
}