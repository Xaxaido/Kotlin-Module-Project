class Decor {

    companion object {

        private const val ASTERISK_COUNT = 5
        private const val PADDING = 2
        private const val DIVIDER = '-'
        private const val DECOR_SIDE = '|'

        @Suppress("UNCHECKED_CAST")
        inline fun <reified T> getMaxWidth(list: List<T>): Int {
            val result = if (list.isNotEmpty()) {

                when (list.first()) {
                    is Data -> list.map { (it as Data).name }
                    else -> list as List<String>
                }
            } else return 0

            return result.sortedByDescending { it.length }.take(1).toString().length
        }

        fun makeHeader(header: String, width: Int): String {
            return "*".repeat(
                if (width == 0 || width <= header.length) ASTERISK_COUNT else width
            ).let { "$it $header $it" }
        }

        fun makeFrame(note: Data.Note) {
            val list = note.content.split("\n")
                .filter(String::isNotEmpty).toList()
            val width = getMaxWidth(list)
            val divider: () -> Unit = {
                println("+${DIVIDER.toString().repeat(width + 2 * PADDING)}+")
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