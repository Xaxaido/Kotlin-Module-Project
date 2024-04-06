interface Mutable {

    fun <T> addValue(list: List<T>, newValue: T) = list.toList() + newValue
    fun <T> removeLast(list: List<T>) = list.subList(0, list.lastIndex)
}