interface Data {

    val name: String
    val strList: String
    val strCreate: String
    val strEnterName: String

    companion object {
        const val OUT_OF_RANGE = "Элемента с таким номером не существует"
    }
}