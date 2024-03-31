import java.util.Scanner
class Menu {

    private val sc = Scanner(System.`in`)
    private val archives: MutableList<Archive> = mutableListOf()
    private val navigation = MenuNavigation(ARCHIVE)
    private var back = EXIT

    private val archiveScreen: (MutableList<Archive>) -> Unit = {
        println("Список архивов:")
        println("0. Создать архив")
        showMenu(it)
    }

    private val createArchive: () -> Unit = {
        println("Введите название архива")
        archives.add(Archive(getEntryInput(), mutableListOf<Note>()))
        navigation.archiveId = archives.size - 1
        navigation.screens.add(NOTE)
        noteScreen(archives[archives.size - 1].data)
    }

    private val noteScreen: (MutableList<Note>) -> Unit = {
        println("Список заметок:")
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

        archives[navigation.archiveId].data.add(Note(name, content.toString()))
        noteScreen(archives[navigation.archiveId].data)
    }

    private val openNote: (Note) -> Unit = {
        println("Заметка ${it.name}")
        println(it.content)

        do {
            println("0. Выход")
            val str = sc.nextLine()
        } while (str != "0")

        navigation.screens.removeLast()
        noteScreen(archives[navigation.archiveId].data)
    }

    fun start() { archiveScreen(archives) }

    private fun getEntryInput(): String {
        var input: String

        do {
            println("Поле не может быть пустым")
            input = sc.nextLine()
        } while (input.isEmpty())

        return input
    }

    private fun showMenu(list: List<Data>) {
        list.forEachIndexed { i, e -> println("${i + 1}. ${e.name}") }
        back =  list.size + 1
        println("$back. Выход")
        getUserInput()
    }

    private fun draw(id: Int) {

        when (id) {
            ARCHIVE -> archiveScreen(archives)
            CREATE_ARCHIVE -> createArchive()
            OPEN_ARCHIVE -> noteScreen(archives[navigation.archiveId].data)
            NOTE -> noteScreen(archives[navigation.archiveId].data)
            CREATE_NOTE -> createNote()
            OPEN_NOTE -> openNote(archives[navigation.archiveId].data[navigation.noteId])
        }

    }

    private fun getScreenById(id: Int, isBackPressed: Boolean): Int {
        return if (navigation.screens.size == 1 && isBackPressed) EXIT else navigation.screens[navigation.screens.size - id]
    }

    private fun isOutOfRange(id: Int): Int? {
        val list = when (getScreenById(CURRENT, false)) {
            ARCHIVE -> archives
            NOTE -> archives[navigation.archiveId].data
            else -> null
        }

        return if (id > list!!.size) {
            list.first().onError()
            null
        } else id
    }

    private fun getItemScreen(id: Int): Int {
        val screen = getScreenById(CURRENT, false)

        return when {
            id > CREATE && screen == ARCHIVE -> NOTE
            id > CREATE && screen == NOTE -> OPEN_NOTE
            else -> screen
        }
    }

    private fun getScreen(id: Int): Int {
        val screens = navigation.screens
        val currentScreen = getScreenById(CURRENT, false)

        return when {
            currentScreen == back -> screens[screens.size - 2]
            id == CREATE -> {
                if (currentScreen == ARCHIVE) CREATE_ARCHIVE else CREATE_NOTE
            }
            currentScreen == NOTE && id > CREATE -> {
                navigation.archiveId = id - 1
                OPEN_ARCHIVE
            }
            currentScreen == OPEN_NOTE && id > CREATE -> {
                navigation.noteId =  id - 1
                OPEN_NOTE
            }
            else -> currentScreen
        }
    }

    private fun getUserInput() {
        var id: Int?
        var isCorrect = true

        do {

            val input = sc.nextLine()
            id = input.toIntOrNull()

            isCorrect = when (id) {
                null -> { println("Введите число"); false }
                CREATE -> true
                back -> {
                    id = getScreenById(CURRENT, true).let { if (it == ARCHIVE) EXIT else it }
                    navigation.screens.removeLast()
                    true
                }
                else -> {
                    if (isOutOfRange(id) != null) {
                        navigation.screens.add(getItemScreen(id))
                        true
                    } else false
                }
            }

        } while(!isCorrect)

        if (id == EXIT) return else draw(getScreen(id!!))

    }

    companion object {
        const val CURRENT = 1
        const val EXIT = -1
        const val CREATE = 0
        const val ARCHIVE = -2
        const val CREATE_ARCHIVE = -3
        const val OPEN_ARCHIVE = -4
        const val NOTE = -5
        const val CREATE_NOTE = -6
        const val OPEN_NOTE = -7
    }

}