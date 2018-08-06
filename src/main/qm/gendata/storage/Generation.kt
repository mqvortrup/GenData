package qm.gendata.storage

typealias Key = Int


typealias Value = String

interface Generation {
    val id: Int

    operator fun get(key: Key): Value
    fun length(): Long
}

interface ReadWriteGeneration : Generation {
    operator fun set(key: Key, value: Value)
}
