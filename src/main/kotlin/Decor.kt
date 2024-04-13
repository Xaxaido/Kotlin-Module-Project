class Decor {

    companion object {

        private const val PADDING = 2
        private const val DECOR_SIDE = '|'
        val divider: (Int) -> Unit = { println("+${"-".repeat(it + 2 * PADDING)}+") }

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> getMaxWidth(list: List<T>) = run {
            if (list.isEmpty()) 0
            else {
                when (list.first()) {
                    is Data -> list.map { (it as Data).name }
                    else -> list as List<String>
                }.sortedByDescending { it.length }.take(1).toString().length
            }
        }

        fun makeHeader(header: String, width: Int) = "*".repeat(
            if (width == 0 || width <= header.length) 5 else width).let { "$it $header $it" }

        fun makeFrame(note: Data.Note) {
            val list = note.content.split("\n").filter(String::isNotEmpty).toList()
            val width = getMaxWidth(list)

            println(makeHeader(note.name,(width - note.name.length + 2 * PADDING) / 2))
            list.forEachIndexed { index, entry ->
                if (index == 0) divider(width)
                println(buildString {
                    append(DECOR_SIDE, " ".repeat(PADDING))
                    append(entry)
                    append(" ".repeat(width - entry.length + PADDING), DECOR_SIDE)
                })
                if (index == list.lastIndex) divider(width)
            }
        }
    }
}