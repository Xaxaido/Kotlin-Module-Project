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
}