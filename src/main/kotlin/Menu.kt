import java.util.Scanner

class Menu {

    private val scanner = Scanner(System.`in`)

    private val menuScreen: (List<Data>) -> Unit = {
        with(Nav) {
            val data = when (screens.last { screen -> screen < EXIT }) {
                ARCHIVE -> arrayOf(Archive().strList, Archive().strCreate)
                NOTE -> arrayOf("${Note().strList} ${archive.name}", Note().strCreate)
                else -> arrayOf()
            }

            println("${Decor.makeHeader(data[0])}\n${data[1]}")
            showMenu(it)
        }
    }

    private val addEntry: (Data) -> Unit = {
        with(Nav) {
            println(it.strEnterName)
            val name = getEntryInput()

            when (it) {
                is Archive -> {
                    archives = addValue(archives, Archive(name, listOf()))
                    archiveId = archives.lastIndex
                    screens = addValue(screens, NOTE)
                }
                is Note -> {
                    val content = StringBuilder()

                    println("Введите текст заметки\n0. Сохранить и выйти")
                    do {
                        val isExit = getEntryInput().let { if (it == "0") true else content.append("$it\n").isEmpty() }
                    } while (!isExit)

                    archive.data = addValue(archive.data, Note(name, content.toString()))
                }
            }

            menuScreen(archive.data)
        }
    }

    private val openNote: (Note) -> Unit = {
        Decor.makeFrame(it)
        do {
            println("0. Выход")
        } while (scanner.nextLine() != "0")
        Nav.removeLast()
        menuScreen(Nav.archive.data)
    }

    fun start() { menuScreen(Nav.archives) }

    private fun getEntryInput(): String {
        var input: String

        do {
            input = scanner.nextLine()
        } while (input.isEmpty().apply { if (this) println("Поле не может быть пустым") })

        return input
    }

    private fun showMenu(list: List<Data>) {
        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        Nav.back = (list.size + 1).apply { println("$this. Выход") }
        getUserInput()
    }

    private fun draw(id: Int) {
        with(Nav) {
            when (id) {
                ARCHIVE -> menuScreen(archives)
                CREATE_ARCHIVE -> addEntry(Archive())
                OPEN_ARCHIVE, NOTE -> menuScreen(lastArchive.data)
                CREATE_NOTE -> addEntry(Note())
                OPEN_NOTE -> openNote(archive.data[noteId])
            }
        }
    }

    private fun getScreen(id: Int): Int {
        with (Nav) {
            return getCurrentScreen(false).let {
                when {
                    it == back -> screens[screens.size - 2]
                    id == CREATE -> if (it == ARCHIVE) CREATE_ARCHIVE else CREATE_NOTE
                    id > CREATE && it == NOTE -> { noteId = id - 1; OPEN_ARCHIVE }
                    id > CREATE && it == OPEN_NOTE -> { noteId = id - 1; OPEN_NOTE }
                    else -> it
                }
            }
        }
    }

    private fun getMenuEntry(id: Int): Boolean {
        with (Nav) {
            return isOutOfRange(id).apply {
                if (this) getCurrentScreen(false).let { screen ->
                    screens = addValue(screens, when {
                        id > CREATE && screen == ARCHIVE -> NOTE
                        id > CREATE && screen == NOTE -> OPEN_NOTE
                        else -> screen
                    })
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
                    CREATE -> true
                    back -> {
                        id = getCurrentScreen(true).let { if (it == ARCHIVE) EXIT else it }
                        removeLast()
                    }
                    else -> getMenuEntry(id!!)
                }
            } while (!isCorrect)

            if (id == EXIT) return else draw(getScreen(id!!))
        }
    }
}