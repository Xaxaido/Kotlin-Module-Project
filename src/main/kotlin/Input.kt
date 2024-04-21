class Input {

    fun getUserInput() = run {
        var input: String
        do {
            input = readln()
            val isCorrect= when {
                input.isEmpty() -> { println(Data.EMPTY_INPUT); false }
                Nav.isExist(input) -> { println (Data.DUPLICATE); false }
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
                null -> println(Data.NOT_NUMBER)
                !in 0 until list.size + 1 -> {
                    if (id == back) {
                        id = screens.last().let { if (it == ARCHIVE) EXIT else it }
                        back(); break
                    } else println(Data.OUT_OF_RANGE)
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