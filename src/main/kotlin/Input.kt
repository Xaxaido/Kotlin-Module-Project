class Input {

    fun getUserInput() = run {
        var input: String

        do {
            input = readln()

            val isCorrect = when {
                input.isEmpty() -> println(Str.EMPTY_INPUT.message).let { false }
                Nav.list.firstOrNull { it.name == input } != null ->
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
                        id = screens.last().let { if (it == ARCHIVE) EXIT else it }
                        back(); break
                    } else println(Str.OUT_OF_RANGE.message)
                }
                else -> {
                    if (id != CREATE)
                        screens += if (screens.last() == ARCHIVE) NOTE else OPEN_NOTE
                    break
                }
            }
        }

        id!!
    }
}