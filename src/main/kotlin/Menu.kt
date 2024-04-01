import java.util.Scanner

class Menu {

    private val scanner = Scanner(System.`in`)
    private val archives: MutableList<Archive> = mutableListOf()

    private val archiveScreen: (MutableList<Archive>) -> Unit = {
        println(makeHeader("Список архивов"))
        println("0. Создать архив")
        Navigation.list = archives
        showMenu(it)
    }

    private val createArchive: () -> Unit = {
        println("Введите название архива")
        archives.add(Archive(getEntryInput(), mutableListOf()))

        with (Navigation) {
            archiveId = archives.size - 1
            add(NOTE)
        }

        noteScreen(archives[archives.size - 1].data)
    }

    private val noteScreen: (List<Note>) -> Unit = {
        println(makeHeader("Список заметок архива ${archives[Navigation.archiveId].name}"))
        println("0. Создать заметку")
        Navigation.list = archives[Navigation.archiveId].data
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

        archives[Navigation.archiveId].add(Note(name, content.toString()))
        noteScreen(archives[Navigation.archiveId].data)
    }

    private val openNote: (Note) -> Unit = {
        println(makeHeader(it.name))
        println(it.content)

        do {
            println("0. Выход")
            val str = scanner.nextLine()
        } while (str != "0")

        Navigation.removeLast()
        noteScreen(archives[Navigation.archiveId].data)
    }

    fun start() { archiveScreen(archives) }

    private fun makeHeader(header: String) = "${"*".repeat(Navigation.ASTERISK_COUNT)}$header${"*".repeat(Navigation.ASTERISK_COUNT)}"

    private fun getEntryInput(): String {
        var input: String

        do {
            input = scanner.nextLine()
            if (input.isEmpty()) println("Поле не может быть пустым")
        } while (input.isEmpty())

        return input
    }

    private fun showMenu(list: List<Data>) {
        Navigation.back =  list.size + 1
        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        println("$Navigation.back. Выход")
        getUserInput()
    }

    private fun openEntries(id: Int): Int {
        val screen = Navigation.getCurrentScreen(false)

        return when {
            id > Navigation.CREATE && screen == Navigation.ARCHIVE -> Navigation.NOTE
            id > Navigation.CREATE && screen == Navigation.NOTE -> Navigation.OPEN_NOTE
            else -> screen
        }
    }

    private fun draw(id: Int) {

        when (id) {
            Navigation.ARCHIVE -> archiveScreen(archives)
            Navigation.CREATE_ARCHIVE -> createArchive()
            Navigation.OPEN_ARCHIVE -> noteScreen(archives[Navigation.archiveId].data)
            Navigation.NOTE -> noteScreen(archives[Navigation.archiveId].data)
            Navigation.CREATE_NOTE -> createNote()
            Navigation.OPEN_NOTE -> openNote(archives[Navigation.archiveId].data[Navigation.noteId])
        }
    }

    private fun getScreen(id: Int): Int {
        val currentScreen = Navigation.getCurrentScreen(false)

        return when {
            currentScreen == Navigation.back -> Navigation.screens[Navigation.screens.size - 2]
            id == Navigation.CREATE -> if (currentScreen == Navigation.ARCHIVE) Navigation.CREATE_ARCHIVE else Navigation.CREATE_NOTE
            currentScreen == Navigation.NOTE && id > Navigation.CREATE -> {
                Navigation.archiveId = id - 1
                Navigation.OPEN_ARCHIVE
            }
            currentScreen == Navigation.OPEN_NOTE && id > Navigation.CREATE -> {
                Navigation.noteId =  id - 1
                Navigation.OPEN_NOTE
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
                Navigation.CREATE -> true
                Navigation.back -> {
                    id = Navigation.getCurrentScreen(true).let { if (it == Navigation.ARCHIVE) Navigation.EXIT else it }
                    Navigation.removeLast()
                    true
                }
                else -> {
                    if (Navigation.isOutOfRange(id) != null) {
                        Navigation.add(openEntries(id))
                        true
                    } else false
                }
            }

        } while(!isCorrect)

        if (id == Navigation.EXIT) return else draw(getScreen(id!!))

    }
}