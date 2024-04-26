import kotlin.system.exitProcess

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
        var id: Int? = null

        do {
            readln().toIntOrNull()?.let { id = it } ?: println(Str.NOT_NUMBER.message)
            if (id != null) id = when (id) {
                !in 0 .. list.size -> {
                    if (id == back) back() ?: exitProcess(0)
                    else println(Str.OUT_OF_RANGE.message).let { null }
                }
                CREATE -> if (screens.last() == ARCHIVE_LIST) CREATE_ARCHIVE else CREATE_NOTE
                else -> getNotes(id!!)
            }
        } while (id == null)

        id!!
    }
}