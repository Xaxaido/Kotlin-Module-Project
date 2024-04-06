data class Archive(

    override val name: String = "",
    var data: List<Note> = listOf()
) : Data, Mutable {
    
    companion object {
        val text = mapOf(
            "List" to "Cписок архивов",
            "Create" to "0. Создать архив",
            "Enter" to "Введите название архива"
        )
    }
}