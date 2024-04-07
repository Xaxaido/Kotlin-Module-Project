class Decor {

    companion object {

        private const val ASTERISK_COUNT = 5
        private const val PADDING = 2
        private const val DIVIDER = '-'
        private const val DECOR_SIDE = '|'

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> getMaxWidth(temp: List<T>): Int {
            val list = if (temp.isNotEmpty()) {

                when (temp.first()) {
                    is Data -> temp.map { (it as Data).name }
                    else -> temp as List<String>
                }
            } else return 0

            return list.sortedByDescending { it.length }.take(1).toString().length
        }

        fun makeHeader(header: String, temp: Int): String {
            val width = if (temp == 0 || temp <= header.length) {
                ASTERISK_COUNT
            } else (temp - header.length) / 2

            return "*".repeat(width).let { "$it $header $it" }
        }

        fun makeFrame(note: Note) {
            val list = note.content.split("\n")
                .filter(String::isNotEmpty).toList()

            val width = getMaxWidth(list)

            val divider: () -> Unit = {
                println(DIVIDER.toString().repeat(width + 2 * PADDING + 2))
            }

            println(makeHeader(note.name,
                        (width - note.name.length + 2 * PADDING) / 2))

            list.forEachIndexed { i, e ->
                if (i == 0) divider()

                println("$DECOR_SIDE ${" "
                    .repeat(PADDING)}$e${" "
                    .repeat(width - e.length + PADDING / 2)}$DECOR_SIDE")

                if (i == list.lastIndex) divider()
            }

        }

    }
}