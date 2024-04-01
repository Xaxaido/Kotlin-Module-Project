import java.util.Scanner

class Menu {

    private val scanner = Scanner(System.`in`)
    private val archives: MutableList<Archive> = mutableListOf()
    private val nav = MenuNavigation(ARCHIVE)
    private var back = EXIT

    private val archiveScreen: (MutableList<Archive>) -> Unit = {
        println(makeHeader("Список архивов"))
        println("0. Создать архив")
        nav.list = archives
        showMenu(it)
    }

    private val createArchive: () -> Unit = {
        println("Введите название архива")
        archives.add(Archive(getEntryInput(), mutableListOf()))

        with (nav) {
            archiveId = archives.size - 1
            add(NOTE)
        }

        noteScreen(archives[archives.size - 1].data)
    }

    private val noteScreen: (List<Note>) -> Unit = {
        println(makeHeader("Список заметок архива ${archives[nav.archiveId].name}"))
        println("0. Создать заметку")
        nav.list = archives[nav.archiveId].data
        showMenu(it)
    }

    private val createNote: () -> Unit = {
        println("Введите название заметки")

        val name = getEntryInput()
        val content = StringBuilder()

        println("Введите текст заметки\n0. Сохранить и выйти")
        while (true) {

            val str = getEntryInput()
            if (str == "0") break
            content.append("$str\n")
        }

        archives[nav.archiveId].add(Note(name, content.toString()))
        noteScreen(archives[nav.archiveId].data)
    }

    private val openNote: (Note) -> Unit = {
        println(makeHeader(it.name))
        println(it.content)

        do {
            println("0. Выход")
            val str = scanner.nextLine()
        } while (str != "0")

        nav.removeLast()
        noteScreen(archives[nav.archiveId].data)
    }

    fun start() { archiveScreen(archives) }

    private fun makeHeader(header: String) = "${"*".repeat(ASTERISK_COUNT)}$header${"*".repeat(ASTERISK_COUNT)}"

    private fun getEntryInput(): String {
        var input: String

        do {
            input = scanner.nextLine()
            if (input.isEmpty()) println("Поле не может быть пустым")
        } while (input.isEmpty())

        return input
    }

    private fun showMenu(list: List<Data>) {
        back =  list.size + 1
        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        println("$back. Выход")
        getUserInput()
    }

    private fun openEntries(id: Int): Int {
        val screen = nav.getCurrentScreen(false)

        return when {
            id > CREATE && screen == ARCHIVE -> NOTE
            id > CREATE && screen == NOTE -> OPEN_NOTE
            else -> screen
        }
    }

    private fun draw(id: Int) {

        when (id) {
            ARCHIVE -> archiveScreen(archives)
            CREATE_ARCHIVE -> createArchive()
            OPEN_ARCHIVE -> noteScreen(archives[nav.archiveId].data)
            NOTE -> noteScreen(archives[nav.archiveId].data)
            CREATE_NOTE -> createNote()
            OPEN_NOTE -> openNote(archives[nav.archiveId].data[nav.noteId])
        }
    }

    private fun getScreen(id: Int): Int {
        val currentScreen = nav.getCurrentScreen(false)

        return when {
            currentScreen == back -> nav.screens[nav.screens.size - 2]
            id == CREATE -> if (currentScreen == ARCHIVE) CREATE_ARCHIVE else CREATE_NOTE
            currentScreen == NOTE && id > CREATE -> {
                nav.archiveId = id - 1
                OPEN_ARCHIVE
            }
            currentScreen == OPEN_NOTE && id > CREATE -> {
                nav.noteId =  id - 1
                OPEN_NOTE
            }
            else -> currentScreen
        }
    }

    private fun getUserInput() {
        var id: Int?
        var isCorrect: Boolean

        do {

            val input = scanner.nextLine()
            id = input.toIntOrNull()

            isCorrect = when (id) {
                null -> { println("Введите число"); false }
                CREATE -> true
                back -> {
                    id = nav.getCurrentScreen(true).let { if (it == ARCHIVE) EXIT else it }
                    nav.removeLast()
                    true
                }
                else -> {
                    if (nav.isOutOfRange(id) != null) {
                        nav.add(openEntries(id))
                        true
                    } else false
                }
            }

        } while(!isCorrect)

        if (id == EXIT) return else draw(getScreen(id!!))

    }

     companion object {
        const val EXIT = -1
        private const val CREATE = 0
        private const val ARCHIVE = -2
        private const val CREATE_ARCHIVE = -3
        private const val OPEN_ARCHIVE = -4
        private const val NOTE = -5
        private const val CREATE_NOTE = -6
        private const val OPEN_NOTE = -7
        private const val ASTERISK_COUNT = 5
    }

}