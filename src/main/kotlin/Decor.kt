class Decor {

    companion object {

        private const val PADDING = 2
        private const val DIVIDER = '-'
        private const val DECOR_SIDE = '|'

        fun makeHeader(header: String, width: Int) = "*".repeat(width).let { "$it $header $it" }

        fun makeFrame(note: Note) {

            val list = note.content.split("\n").toList()
            val width = list.sortedByDescending { it.length }.take(1).toString().length
            val divider: () -> Unit = { println(DIVIDER.toString().repeat(width + 2 * PADDING + 3)) }

            println(makeHeader(note.name, width / 2 + PADDING))
            list.forEachIndexed { i, e ->
                if (i == 0) divider()
                println("$DECOR_SIDE ${" ".repeat(PADDING)}$e${" ".repeat(width + PADDING - e.length)}$DECOR_SIDE")
                if (i == list.size - 1) divider()
            }
        }
    }
}