package qm.gendata.storage.spi

import qm.gendata.storage.Key
import qm.gendata.storage.ReadWriteGeneration
import qm.gendata.storage.Value

class GenerationImpl : ReadWriteGeneration {

    private var discarded = false

    override fun length(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

   override fun put(key: Key, value: Value) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(key: Key): Value {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun copy(): GenerationImpl {
        TODO("not implemented")
    }

    fun discard() {
        discarded = true
    }
}