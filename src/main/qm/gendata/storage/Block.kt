package qm.gendata.storage

interface Block {
    val capacity: Int

    operator fun set(key: Key, value: Value)

    operator fun get(key: Key): Value
}

 interface BlockList {
    val size: Int

    operator fun get(id: Int): Block
}