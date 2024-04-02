data class Note(

    override val name: String,
    val content: String
) : Data {

    companion object {
        const val stringList = "Список заметок архива"
        const val stringCreate = "0. Создать заметку"
    }

}