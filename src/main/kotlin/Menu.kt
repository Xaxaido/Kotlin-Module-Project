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

    private val openNote: (Note) -> Unit = {
        Decor.makeFrame(it)
        do {
            println("0${Str.EXIT.message}")
        } while (readln() != "0")
        Nav.back()
    }

    private inline fun <reified T> showMenu(
        list: MutableList<T>,
        onStart: () -> Unit = {},
    ): Unit = with (Nav) {
        onStart()
        val extra = if (T::class.qualifiedName == "Note") archive.name else ""
        Decor.makeHeader(Str.text("${getClass(list)}_LIST") + extra)
        println(Str.text("${getClass(list)}_CREATE"))

        list.forEachIndexed { i, e -> println("${i + 1}. $e") }

        back = (list.size + 1).apply {
            println("$this${Str.EXIT.message}")
        }
        draw(input.getScreen())
    }

    private inline fun <reified T> add(list: MutableList<T>, onAdd: () -> Any) {
        println(Str.text("${Nav.getClass(list)}_ENTER_NAME"))
        list.add(
            T::class.java.constructors.first { it.parameterTypes.size == 2 }
                .newInstance(input.getUserInput(), onAdd()) as T
        )
    }

    fun draw(screen: Int = Nav.ARCHIVE_LIST) = with (Nav) {
        if (screen == ARCHIVE_LIST) showMenu(archives)
        else showMenu(archive.data) {
            when (screen) {
                CREATE_ARCHIVE -> add(archives) {
                        screens += NOTE_LIST
                        mutableListOf<Note>()
                    }
                CREATE_NOTE -> add(archive.data, createNote)
                OPEN_NOTE -> openNote(archive.data[noteId])
            }
        }
    }
}