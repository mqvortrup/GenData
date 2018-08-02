package qm.gendata.storage

interface Key {

}

interface Value {

}

interface Generation {
    val id: Int

    fun get(key: Key): Value
    fun length(): Long
}

interface ReadWriteGeneration : Generation {
    fun put(key: Key, value: Value)
}
