sealed class Data(val name: String) {

    class Archive(

        name: String = "",
        var data: List<Note> = listOf()
    ) : Data(name), Mutable {

        companion object {
            val text = mapOf(
                "List" to "Cписок архивов",
                "Create" to "0. Создать архив",
                "Enter" to "Введите название архива",
            )
        }
    }

    class Note(

        name: String = "",
        val content: String = ""
    ) : Data(name) {

        companion object {
            val text = mapOf (
                "List" to "Список заметок архива ",
                "Create" to "0. Создать заметку",
                "Enter" to "Введите название заметки",
            )
        }
    }

    companion object {
        const val OUT_OF_RANGE = "Элемента с таким номером не существует"
    }
}