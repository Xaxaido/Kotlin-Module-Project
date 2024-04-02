data class Archive(

    override val name: String,
    var data: List<Note>
) : Data, Mutable<Note, Nothing> {

    override fun add(newValue: Note) { data = copy().data + newValue }
    override fun addArchive(newValue: Nothing) {}
    override fun removeLast() {}

    companion object {
        const val stringList = "Cписок архивов"
        const val stringCreate = "0. Создать архив"
    }
}