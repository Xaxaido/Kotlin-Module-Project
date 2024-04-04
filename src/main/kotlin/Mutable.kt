interface Mutable<T, E> {

    fun addValue(newValue: T)
    fun addEntry(newEntry: E) {}
    fun removeLast() {}
}