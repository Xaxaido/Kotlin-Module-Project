class Input {

    fun getUserInput() = run {
        var input: String
        do {
            input = readln()
            val isCorrect = when {
                input.isEmpty() -> { println(STR.System.EMPTY_INPUT.message); false }
                Nav.list.firstOrNull { it.name == input } != null -> {
                    println(STR.System.DUPLICATE.message); false
                }
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
                null -> println(STR.System.NOT_NUMBER.message)
                !in 0 .. list.size -> {
                    if (id == back) {
                        id = screens.last().let { if (it == ARCHIVE) EXIT else it }
                        back(); break
                    } else println(STR.System.OUT_OF_RANGE.message)
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