data class Note(

    override val name: String = "",
    val content: String = ""
) : Data {

    companion object {
        val text = mapOf (
            "List" to "Список заметок архива ",
            "Create" to "0. Создать заметку",
            "Enter" to "Введите название заметки"
        )
    }
}