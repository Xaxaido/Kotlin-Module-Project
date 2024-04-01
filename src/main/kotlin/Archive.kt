data class Archive(

    override val name: String,
    var data: List<Note>
) : Data, Mutable<Note> {
    override fun add(newValue: Note) { this.data = this.copy().data + newValue }

    override fun removeLast() { this.data = this.copy().data.subList(0, this.data.size - 1) }
}