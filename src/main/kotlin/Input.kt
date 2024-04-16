import java.util.Scanner

class Input {

    val scanner = Scanner(System.`in`)

    fun getUserInput() = run {
        var input: String

        do {
            input = scanner.nextLine()
        } while (input.isEmpty().apply { if (this) println(Data.EMPTY_INPUT) })

        input
    }

    fun getMenuInput() = with(Nav) {
        var id: Int?

        do {
            id = scanner.nextLine().toIntOrNull()

            val isCorrect = when (id) {
                null -> { println(Data.NOT_NUMBER); false }
                CREATE -> true
                back -> {
                    id = screens.last().let {
                        if (it == ARCHIVE) EXIT else it
                    }
                    screens -= screens.last()
                    true
                }
                in list.size + 1 until Int.MAX_VALUE,
                in Int.MIN_VALUE until CREATE -> { println(Data.OUT_OF_RANGE); false }
                else -> {
                    screens += if (screens.last() == ARCHIVE) NOTE else OPEN_NOTE
                    true
                }
            }
        } while (!isCorrect)

        id
    }
}