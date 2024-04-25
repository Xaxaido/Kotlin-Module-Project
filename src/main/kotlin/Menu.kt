class Menu {

    private val input = Input()

    private val createNote: () -> Any = {
        val content = StringBuilder()
        println(Str.NOTE_ENTER_TEXT.message)
        do {
            val input = input.getUserInput()
        } while ((input != "0").apply {
            if (this) content.appendLine(input)
        })
        content.toString()
    }

    private inline fun <reified T> showMenu(
        list: MutableList<T>,
        extra: String = "",
    ): Unit = with (Nav) {
        Decor.makeHeader(buildString {
            append(Str.text("${getClass(list)}_LIST"))
            append(extra)
        })
        println(Str.text("${getClass(list)}_CREATE"))

        list.forEachIndexed { i, e -> println("${i + 1}. $e") }

        back = (list.size + 1).apply {
            println("$this${Str.EXIT.message}")
        }
        input.getMenuInput().let {
            if (it == EXIT) return else draw(it)
        }
    }

    private inline fun <reified T> add(
        list: MutableList<T>,
        onAdd: () -> Any
    ) = with (Nav) {
        println(Str.text("${getClass(list)}_ENTER_NAME"))
        list.add(
            T::class.java.constructors.first { it.parameterTypes.size == 2 }
                .newInstance(input.getUserInput(), onAdd()) as T
        )
        showMenu(archive.data, archive.name)
    }

    fun start() { draw(Nav.ARCHIVE_LIST) }

    private fun openNote(note: Note) = with (Nav) {
        Decor.makeFrame(note)
        do {
            println("0${Str.EXIT.message}")
        } while (readln() != "0")
        back()
        showMenu(archive.data, archive.name)
    }

    private fun draw(screen: Int) = with (Nav) {
        when (screen) {
            ARCHIVE_LIST -> showMenu(archives)
            CREATE_ARCHIVE -> add(archives) {
                screens += NOTE_LIST
                mutableListOf<Note>()
            }
            NOTE_LIST -> showMenu(archive.data, archive.name)
            CREATE_NOTE -> add(archive.data, createNote)
            OPEN_NOTE -> openNote(archive.data[noteId])
        }
    }
}