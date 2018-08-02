package qm.gendata.storage.spi

import qm.gendata.storage.Key
import qm.gendata.storage.ReadWriteGeneration
import qm.gendata.storage.Value

class GenerationImpl(override val id: Int) : ReadWriteGeneration {

    private var discarded = false

    override fun length(): Long {
        assert(!discarded, { "The generation is discarded"})
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

   override fun put(key: Key, value: Value) {
       assert(!discarded, { "The generation is discarded"})
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(key: Key): Value {
        assert(!discarded, { "The generation is discarded"})
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun copy(): GenerationImpl {
        assert(!discarded, { "The generation is discarded"})
        TODO("not implemented")
    }

    fun discard() {
        discarded = true
    }

    fun delete() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}