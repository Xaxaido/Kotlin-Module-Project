data class Archive(
    override val name: String,
    val data: MutableList<Note>
) : Data {
    override fun onError() { println("Архива с таким номером не существует") }
}