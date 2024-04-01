import java.util.Scanner

class Menu {

    private val scanner = Scanner(System.`in`)

    private val archiveScreen: (MutableList<Archive>) -> Unit = {
        println(makeHeader("Список архивов"))
        println("0. Создать архив")
        showMenu(it)
    }

    private val createArchive: () -> Unit = {
        println("Введите название архива")
        with (Nav) {
            archives.add(Archive(getEntryInput(), mutableListOf()))
            archiveId = archives.size - 1
            add(NOTE)
        }
        noteScreen(Nav.archives[Nav.archiveId].data)
    }

    private val noteScreen: (List<Data>) -> Unit = {
        println(makeHeader("Список заметок архива ${Nav.archives[Nav.archiveId].name}"))
        println("0. Создать заметку")
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

        Nav.archives[Nav.archives.size - 1].add(Note(name, content.toString()))
        noteScreen(Nav.archives[Nav.archiveId].data)
    }

    private val openNote: (Note) -> Unit = {
        println(makeHeader(it.name))
        println(it.content)

        do {
            println("0. Выход")
            val str = scanner.nextLine()
        } while (str != "0")

        Nav.removeLast()
        noteScreen(Nav.archives[Nav.archiveId].data)
    }

    fun start() { archiveScreen(Nav.archives) }

    private fun makeHeader(header: String) = "${"*".repeat(Nav.ASTERISK_COUNT)}$header${"*".repeat(Nav.ASTERISK_COUNT)}"

    private fun getEntryInput(): String {
        var input: String

        do {
            input = scanner.nextLine()
            if (input.isEmpty()) println("Поле не может быть пустым")
        } while (input.isEmpty())

        return input
    }

    private fun showMenu(list: List<Data>) {
        Nav.back =  list.size + 1
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

        when (id) {
            Nav.ARCHIVE -> archiveScreen(Nav.archives)
            Nav.CREATE_ARCHIVE -> createArchive()
            Nav.OPEN_ARCHIVE -> noteScreen(Nav.archives[Nav.archives.size - 1].data)
            Nav.NOTE -> noteScreen(Nav.archives[Nav.archives.size - 1].data)
            Nav.CREATE_NOTE -> createNote()
            Nav.OPEN_NOTE -> openNote(Nav.archives[Nav.archiveId].data[Nav.noteId])
        }
    }

    private fun getScreen(id: Int): Int {
        val currentScreen = Nav.getCurrentScreen(false)

        return when {
            currentScreen == Nav.back -> Nav.screens[Nav.screens.size - 2]
            id == Nav.CREATE -> if (currentScreen == Nav.ARCHIVE) Nav.CREATE_ARCHIVE else Nav.CREATE_NOTE
            currentScreen == Nav.NOTE && id > Nav.CREATE -> { Nav.archiveId = id - 1; Nav.OPEN_ARCHIVE }
            currentScreen == Nav.OPEN_NOTE && id > Nav.CREATE -> {  Nav.noteId =  id - 1; Nav.OPEN_NOTE }
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
                Nav.CREATE -> true
                Nav.back -> {
                    id = Nav.getCurrentScreen(true).let { if (it == Nav.ARCHIVE) Nav.EXIT else it }
                    Nav.removeLast()
                    true
                }
                else -> {
                    if (Nav.isOutOfRange(id) != null) {
                        Nav.add(openEntries(id))
                        true
                    } else false
                }
            }

        } while(!isCorrect)

        if (id == Nav.EXIT) return else draw(getScreen(id!!))

    }
}