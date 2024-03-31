data class Archive(

    override val name: String,
    val data: MutableList<Note>
) : Data