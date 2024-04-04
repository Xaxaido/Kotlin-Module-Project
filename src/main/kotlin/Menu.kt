import java.util.Scanner

class Menu {

    private val scanner = Scanner(System.`in`)

    private val menuScreen: (List<Data>) -> Unit = {
        var stringList = ""
        var stringCreate = ""

        with (Nav) {
            when (screens.last { screen -> screen < EXIT }) {
                ARCHIVE -> {
                    stringList = Archive.STR_LIST
                    stringCreate = Archive.STR_CREATE
                }
                NOTE -> {
                    stringList = "${Note.STR_LIST} ${archive.name}"
                    stringCreate = Note.STR_CREATE
                }
            }
        }

        println("${Decor.makeHeader(stringList, Nav.ASTERISK_COUNT)}\n$stringCreate")
        showMenu(it)
    }

    private val createArchive: () -> Unit = {
        println(Archive.STR_ENTER_NAME)
        with (Nav) {
            addEntry(Archive(getEntryInput(), listOf()))
            archiveId = archives.lastIndex
            addValue(NOTE)
            menuScreen(archive.data)
        }
    }

    private val createNote: () -> Unit = {
        println(Note.STR_ENTER_NAME)

        val name = getEntryInput()
        val content = StringBuilder()

        println("Введите текст заметки\n0. Сохранить и выйти")
        do {
            val isExit = getEntryInput().let { if (it == "0") true else content.append("$it\n").isEmpty() }
        } while (!isExit)

        Nav.archive.addValue(Note(name, content.toString()))
        menuScreen(Nav.archive.data)
    }

    private val openNote: (Note) -> Unit = {
        Decor.makeFrame(it)

        do { println("0. Выход") } while (scanner.nextLine() != "0")

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

    private fun openEntries(id: Int): Int {
        val screen = Nav.getCurrentScreen(false)

        return when {
            id > Nav.CREATE && screen == Nav.ARCHIVE -> Nav.NOTE
            id > Nav.CREATE && screen == Nav.NOTE -> Nav.OPEN_NOTE
            else -> screen
        }
    }

    private fun draw(id: Int) {
        with (Nav) {
            when (id) {
                ARCHIVE -> menuScreen(archives)
                CREATE_ARCHIVE -> createArchive()
                OPEN_ARCHIVE -> menuScreen(lastArchive.data)
                NOTE -> menuScreen(lastArchive.data)
                CREATE_NOTE -> createNote()
                OPEN_NOTE -> openNote(archive.data[noteId])
            }
        }
    }

    private fun getScreen(id: Int): Int {
        with (Nav) {
            val screen = getCurrentScreen(false)

            return when {
                screen == back -> screens[screens.size - 2]
                id == CREATE -> if (screen == ARCHIVE) CREATE_ARCHIVE else CREATE_NOTE
                screen == NOTE && id > CREATE -> { archiveId = id - 1; OPEN_ARCHIVE }
                screen == OPEN_NOTE && id > CREATE -> { noteId = id - 1; OPEN_NOTE }
                else -> screen
            }
        }
    }

    private fun getUserInput() {
        var id: Int?
        var isCorrect: Boolean

        with (Nav) {
            do {

                id = scanner.nextLine().toIntOrNull()

                isCorrect = when (id) {
                    null -> { println("Введите число"); false }
                    CREATE -> true
                    back -> {
                        id = getCurrentScreen(true).let { if (it == ARCHIVE) EXIT else it }
                        removeLast()
                        true
                    }
                    else -> {
                        if (isOutOfRange(id!!) != null) {
                            addValue(openEntries(id!!))
                            true
                        } else false
                    }
                }

            } while (!isCorrect)

        if (id == EXIT) return else draw(getScreen(id!!))
        }
    }
}