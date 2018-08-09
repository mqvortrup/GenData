package qm.gendata.storage.spi

import qm.gendata.storage.BlockList
import qm.gendata.storage.Key
import qm.gendata.storage.ReadWriteGeneration
import qm.gendata.storage.Value

class GenerationImpl(override val id: Int, private val blocks: BlockList, private val blockMap: MutableMap<Int, Int>) : ReadWriteGeneration {

    private var discarded = false

    override fun length(): Long {
        require(!discarded, { "The generation $id is discarded, and cannot be used"})
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

   override fun set(key: Key, value: Value) {
       require(!discarded, { "The generation $id is discarded, and cannot be used"})
       blocks[key / blocks.size][key] = value
    }

    override fun get(key: Key): Value {
        require(!discarded, { "The generation $id is discarded, and cannot be used"})
        return blocks[key / blocks.size][key]
    }

    fun copy(): GenerationImpl {
        require(!discarded, { "The generation $id is discarded, and cannot be used"})
        return GenerationImpl(id+1, blocks, HashMap(blockMap))
    }

    fun discard() {
        discarded = true
    }

    fun delete() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}