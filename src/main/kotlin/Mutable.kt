interface Mutable {

    fun <T> addValue(list: List<T>, newValue: T) = list.toList() + newValue
    fun removeLast() = true
}