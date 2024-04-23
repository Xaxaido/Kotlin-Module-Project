sealed class Data(
    val name: String,
) {

    class Archive(
        name: String = "",
        val data: MutableList<Note> = mutableListOf(),
    ) : Data(name)

    class Note(
        name: String = "",
        val content: String = "",
    ) : Data(name)

    companion object {
        inline fun <reified T> getClass(list: MutableList<T>) = T::class.simpleName?.uppercase()
    }
}