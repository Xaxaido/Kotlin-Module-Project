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
                    stringList = "${Note.STR_LIST} ${archives[archiveId].name}"
                    stringCreate = Note.STR_CREATE
                }
            }
        }

        println(Decor.makeHeader(stringList, Nav.ASTERISK_COUNT))
        println(stringCreate)
        showMenu(it)
    }

    private val createArchive: () -> Unit = {
        println(Archive.STR_ENTER_NAME)

        with (Nav) {
            addArchive(Archive(getEntryInput(), listOf()))
            archiveId = archives.size - 1
            add(NOTE)
            menuScreen(archives[archiveId].data)
        }
    }

    private val createNote: () -> Unit = {
        println(Note.STR_ENTER_NAME)

        val name = getEntryInput()
        val content = StringBuilder()

        println("Введите текст заметки\n0. Сохранить и выйти")
        while (true) {

            val text = getEntryInput()
            if (text == "0") break
            content.append("$text\n")
        }

        Nav.archives[Nav.archives.size - 1].add(Note(name, content.toString()))
        menuScreen(Nav.archives[Nav.archiveId].data)
    }

    private val openNote: (Note) -> Unit = {
        Decor.makeFrame(it)

        do { println("0. Выход") } while (scanner.nextLine() != "0")

        Nav.removeLast()
        menuScreen(Nav.archives[Nav.archiveId].data)
    }

    fun start() { menuScreen(Nav.archives) }

    private fun getEntryInput(): String {
        var input: String

        do {
            input = scanner.nextLine()
            if (input.isEmpty()) println("Поле не может быть пустым")
        } while (input.isEmpty())

        return input
    }

    private fun showMenu(list: List<Data>) {
        Nav.back = list.size + 1
        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        println("${Nav.back}. Выход")
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
                OPEN_ARCHIVE -> menuScreen(archives[archives.size - 1].data)
                NOTE -> menuScreen(archives[archives.size - 1].data)
                CREATE_NOTE -> createNote()
                OPEN_NOTE -> openNote(archives[archiveId].data[noteId])
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
                            add(openEntries(id!!))
                            true
                        } else false
                    }
                }

            } while (!isCorrect)

        if (id == EXIT) return else draw(getScreen(id!!))
        }
    }
}