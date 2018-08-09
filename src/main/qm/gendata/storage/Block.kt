package qm.gendata.storage

interface Block {
    val capacity: Int
    val references: Int
    var updating: Boolean

    operator fun set(key: Key, value: Value)

    operator fun get(key: Key): Value

    fun increaseReferences()
    fun resetReferences()

    fun copyFrom(original: Block)
}

 interface BlockList {
     val size: Int
     val freeBlockCount: Int

     fun allocate(): Int
     fun free(id: Int)

     operator fun get(id: Int): Block
 }
fun newBlockMap(): MutableMap<Int, Int> {
    return HashMap<Int, Int>()
}


