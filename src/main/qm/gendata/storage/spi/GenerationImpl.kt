package qm.gendata.storage.spi

import qm.gendata.storage.BlockList
import qm.gendata.storage.Key
import qm.gendata.storage.ReadWriteGeneration
import qm.gendata.storage.Value

class GenerationImpl(override val id: Int, private val blocks: BlockList) : ReadWriteGeneration {

    private var discarded = false

    override fun length(): Long {
        assert(!discarded, { "The generation is discarded"})
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

   override fun set(key: Key, value: Value) {
       assert(!discarded, { "The generation is discarded"})
       blocks[key / blocks.size][key] = value
    }

    override fun get(key: Key): Value {
        assert(!discarded, { "The generation is discarded"})
        return blocks[key / blocks.size][key]
    }

    fun copy(): GenerationImpl {
        assert(!discarded, { "The generation is discarded"})
        return GenerationImpl(id+1, blocks)
    }

    fun discard() {
        discarded = true
    }

    fun delete() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}