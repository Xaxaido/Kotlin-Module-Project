data class Note(
    override val name: String,
    val content: String
) : Data {
    override fun onError() { println("Заметки с таким номером не существует") }
}