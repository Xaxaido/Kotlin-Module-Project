class STR {

    enum class System(val message: String) {
        OUT_OF_RANGE("Элемента с таким номером не существует"),
        DUPLICATE("Элемент с таким именем уже существует"),
        EMPTY_INPUT("Поле не может быть пустым"),
        NOT_NUMBER("Введите число"),
        EXIT(". Выход"),
    }

    enum class Archive(val message: String) {
        LIST("Список архивов"),
        CREATE("0. Создать архив"),
        ENTER_NAME("Введите название архива");

        companion object {
             fun text(name: String) = valueOf(name).message
        }
    }

    enum class Note(val message: String) {
        LIST("Список заметок архива "),
        CREATE("0. Создать заметку"),
        ENTER_NAME("Введите название заметки"),
        ENTER_TEXT("Введите текст заметки\n0. Сохранить и выйти");

        companion object {
            fun text(name: String) = valueOf(name).message
        }
    }
}