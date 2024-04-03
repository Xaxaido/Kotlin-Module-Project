data class Archive(

    override val name: String,
    var data: List<Note>
) : Data, Mutable<Note, Nothing> {

    override fun addValue(newValue: Note) { data = copy().data + newValue }
    override fun addArchive(newValue: Nothing) {}
    override fun removeLast() {}

    companion object {
        const val STR_LIST = "Cписок архивов"
        const val STR_CREATE = "0. Создать архив"
        const val STR_ENTER_NAME = "Введите название архива"
    }
}