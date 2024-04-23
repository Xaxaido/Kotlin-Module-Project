enum class Str(val message: String) {

    OUT_OF_RANGE("Элемента с таким номером не существует"),
    DUPLICATE("Элемент с таким именем уже существует"),
    EMPTY_INPUT("Поле не может быть пустым"),
    NOT_NUMBER("Введите число"),
    EXIT(". Выход"),

    ARCHIVE_LIST("Список архивов"),
    ARCHIVE_CREATE("0. Создать архив"),
    ARCHIVE_ENTER_NAME("Введите название архива"),

    NOTE_LIST("Список заметок архива "),
    NOTE_CREATE("0. Создать заметку"),
    NOTE_ENTER_NAME("Введите название заметки"),
    NOTE_ENTER_TEXT("Введите текст заметки\n0. Сохранить и выйти");

    companion object {
        fun text(name: String) = valueOf(name).message
    }
}