data class Note(

    override val name: String = "",
    val content: String = ""
) : Data {

    override val strList = "Список заметок архива"
    override val strCreate = "0. Создать заметку"
    override val strEnterName = "Введите название заметки"
}