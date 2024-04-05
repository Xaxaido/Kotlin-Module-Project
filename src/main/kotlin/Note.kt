data class Note(

    override val name: String,
    val content: String
) : Data {

    companion object {
        const val STR_LIST = "Список заметок архива"
        const val STR_CREATE = "0. Создать заметку"
        const val STR_ENTER_NAME = "Введите название заметки"
    }
}