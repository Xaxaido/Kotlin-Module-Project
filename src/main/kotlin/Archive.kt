data class Archive(

    override val name: String = "",
    var data: List<Note> = listOf()
) : Data, Mutable {

    override val strList = "Cписок архивов"
    override val strCreate = "0. Создать архив"
    override val strEnterName = "Введите название архива"
}