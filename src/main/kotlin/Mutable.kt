interface Mutable<T, E> {

    fun addValue(newValue: T)
    fun addArchive(newValue: E)
    fun removeLast()
}