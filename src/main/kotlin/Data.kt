sealed class Data(
    val name: String
) {

    class Archive(
        name: String = "",
        val data: MutableList<Note> = mutableListOf()
    ) : Data(name) {

        companion object {
            val text = getText(listOf("архивов", "архив", "архива"))
        }
    }

    class Note(
        name: String = "",
        val content: String = ""
    ) : Data(name) {

        companion object {
            const val STR_CREATE_NOTE = "Введите текст заметки\n0. Сохранить и выйти"
            val text = getText(listOf("заметок архива ", "заметку", "заметки"))
        }
    }

    companion object {
        const val OUT_OF_RANGE = "Элемента с таким номером не существует"
        const val DUPLICATE = "Элемент с таким именем уже существует"
        const val EMPTY_INPUT = "Поле не может быть пустым"
        const val NOT_NUMBER = "Введите число"

        private fun getText(args: List<String>) = mapOf(
            "List" to "Список ${args[0]}",
            "Create" to "0. Создать ${args[1]}",
            "EnterName" to "Введите название ${args[2]}",
        )
    }
}