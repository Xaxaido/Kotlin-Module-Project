interface Mutable<T, E> {

    fun add(newValue: T)
    fun addArchive(newValue: E)
    fun removeLast()
}