package qm.gendata.storage.spi

import qm.gendata.storage.Block
import qm.gendata.storage.BlockList
import qm.gendata.storage.Key
import qm.gendata.storage.Value

class InMemoryBlocks(override val size: Int = 10, val blockSize: Int = 10) : BlockList {

    override val freeBlockCount: Int
        get() = freeBlocks.size

    private val blocks = mutableListOf<Block>()
    private val freeBlocks = mutableListOf<Int>()

    init {
        for (i in 0..size-1) {
            blocks.add(i, InMemoryBlock(blockSize))
            freeBlocks.add(i)
        }
    }

    override fun allocate(): Int {
        assert(!freeBlocks.isEmpty(), { "Blocklist is exhausted, no free blocks available"} )
        val blockIndex = freeBlocks.removeAt(0)
        blocks[blockIndex].increaseReferences()
        blocks[blockIndex].updatable = true
        return blockIndex
    }

    override fun free(id: Int) {
        assert(!freeBlocks.contains(id), { "Cannot free an already free block $id"} )
        blocks[id].resetReferences()
        freeBlocks.add(id)
    }

    override fun get(id: Int): Block {
        require(id>= 0 && id < size) {"Wrong id $id, must be between 0 and ${size-1} "}
        return blocks[id]
    }

}

class InMemoryBlock(override val capacity: Int, override var updatable: Boolean = false) : Block {

    private var references_: Int = 0

    override val references: Int
        get() = references_

    override fun increaseReferences() {
        references_++
    }

    override fun resetReferences() {
        references_ = 0
    }

    private val entries = MutableList<Value>(capacity) {it -> it.toString()}

    override fun get(key: Key): Value {
        return entries[key % capacity]
    }

    override fun set(key: Key, value: Value) {
        assert(updatable, { "Block is not updatable"} )
        entries[key % capacity] = value
    }

    override fun copyFrom(original: Block) {
        if (original is InMemoryBlock)
            for (id in 0..capacity-1) entries[id] = original.entries[id]
        else
            throw IllegalArgumentException("Cannot copy from $original to $this")
    }
}
