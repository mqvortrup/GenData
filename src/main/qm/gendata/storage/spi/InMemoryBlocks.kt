package qm.gendata.storage.spi

import qm.gendata.storage.Block
import qm.gendata.storage.BlockList
import qm.gendata.storage.Key
import qm.gendata.storage.Value

class InMemoryBlocks(override val size: Int, val blockSize: Int) : BlockList {
    private val blocks = mutableListOf<Block>()

    init {
        for (i in 0..size) blocks.add(i, InMemoryBlock(blockSize))
    }

    override fun get(id: Int): Block {
        assert(id>= 0 && id < size) {"Wrong id $id, must be between 0 and $size "}
        return blocks[id]
    }

}

class InMemoryBlock(override val capacity: Int) : Block {
    private val entries = MutableList<Value>(capacity) {it -> it.toString()}

    override fun get(key: Key): Value {
        return entries[key % capacity]
    }

    override fun set(key: Key, value: Value) {
        entries[key % capacity] = value
    }

}
