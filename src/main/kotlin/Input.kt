class Input {

    fun getUserInput() = run {
        var input: String
        do {
            input = readln()
        } while (input.isEmpty().apply { if (this) println(Data.EMPTY_INPUT) })
        input
    }

    fun getMenuInput() = with(Nav) {
        var id: Int?

        while (true) {
            id = readln().toIntOrNull()
            when (id) {
                null -> println(Data.NOT_NUMBER)
                in list.size + 1 until Int.MAX_VALUE,
                in Int.MIN_VALUE until CREATE -> {
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