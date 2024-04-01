data class Archive(

    override val name: String,
    var data: List<Note>
) : Data, Mutable<Note> {

    override fun add(newValue: Note) { data = copy().data + newValue }
    override fun removeLast() {}
}