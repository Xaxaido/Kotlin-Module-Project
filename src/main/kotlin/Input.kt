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

    fun getMenuInput() = with(Nav) {
        var id: Int?

        while (true) {
            id = readln().toIntOrNull()
            when (id) {
                null -> println(Str.NOT_NUMBER.message)
                !in 0 .. list.size -> {
                    if (id == back) {
                        back()
                        id = screens.lastOrNull() ?: EXIT
                        break
                    } else println(Str.OUT_OF_RANGE.message)
                }
                else -> {
                    val screen = screens.last()
                    if (id != CREATE) {
                        id = if (screen == ARCHIVE_LIST) {
                            archiveId = id - 1
                            NOTE_LIST
                        } else {
                            noteId = id - 1
                            OPEN_NOTE
                        }
                        screens += id
                    }
                    else id = if (screen == ARCHIVE_LIST) CREATE_ARCHIVE else CREATE_NOTE
                    break
                }
            }
        }

        id!!
    }
}