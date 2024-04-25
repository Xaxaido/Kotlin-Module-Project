class Archive(
    val name: String = "",
    val data: MutableList<Note> = mutableListOf(),
) {

    override fun toString() = this.name
}