class Input {

    fun getUserInput() = run {
        var input: String

        do {
            input = readln()
            val isCorrect = when {
                input.isEmpty() -> println(Str.EMPTY_INPUT.message).let { false }
                Nav.list.firstOrNull { it == input } != null ->
                    println(Str.DUPLICATE.message).let { false }
                else -> true
            }
        } while (!isCorrect)

        input
    }

    fun getScreen() = with(Nav) {
        var id: Int?
        val screen = screens.last()

        do {
            id = readln().toIntOrNull()
            id = when (id) {
                null -> println(Str.NOT_NUMBER.message).let { null }
                !in 0 .. list.size -> {
                    if (id == back) {
                        back()
                        screens.lastOrNull() ?: EXIT
                    } else println(Str.OUT_OF_RANGE.message).let { null }
                }
                CREATE -> if (screen == ARCHIVE_LIST) CREATE_ARCHIVE else CREATE_NOTE
                else -> {
                    setId(id)
                    if (screen == ARCHIVE_LIST) NOTE_LIST else OPEN_NOTE
                }
            }
        } while (id == null)

        id
    }
}