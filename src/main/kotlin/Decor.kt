class Decor {

    companion object {

        private const val PADDING = 2
        private const val DECOR_SIDE = '|'
        val divider: (Int) -> Unit = { println("+${"-".repeat(it + 2 * PADDING)}+") }

        fun makeHeader(header: String) = "*".repeat(5).let { println("$it $header $it") }

        fun makeFrame(note: Data.Note) {
            val list = note.content.split("\n").filter(String::isNotEmpty).toList()
            val width = list.sortedByDescending { it.length }.take(1).toString().length
            makeHeader(note.name)
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