package qm.gendata.storage.spi

import qm.gendata.storage.Generation
import qm.gendata.storage.ReadWriteGeneration
import qm.gendata.storage.Store
import kotlin.system.exitProcess

class SimpleStoreImpl(override val name: String) : Store {
    override val generations : List<Generation>
        get() = generations_

    private var generations_ = listOf<GenerationImpl>()
    var newestGeneration : GenerationImpl? = null

    override fun getGeneration(id: Int): Generation {
        return getGenerationImpl(id)
    }

    private fun getGenerationImpl(id: Int): GenerationImpl {
        if (id < 0 || id >= generations_.size)
            throw IllegalArgumentException("No such generation: $id in $name")
        else
            return generations_[id]
    }

    override fun getLatestGeneration(): Generation {
        return getLastestGenerationImpl()
    }

    private fun getLastestGenerationImpl(): GenerationImpl {
        if (generations_.isEmpty())
            throw IllegalArgumentException("No latest generation in $name")
        else
            return generations_.last()
    }

    override fun deleteGeneration(id: Int) {
        val toDelete = getGenerationImpl(id)
        toDelete.discard()
        generations_ = generations_.filter( {it.id != id})
        toDelete.delete()
    }

    @Synchronized
    override fun createNewGeneration(): ReadWriteGeneration {
        if (newestGeneration == null) {
            newestGeneration =
                    when (generations_.isEmpty()) {
                        true -> GenerationImpl(0)
                        false -> getLastestGenerationImpl().copy()
                    }
            return newestGeneration as ReadWriteGeneration
        } else
            throw IllegalStateException("a new generation already exists")
    }

    @Synchronized
    override fun publishNewGeneration() {
        if (newestGeneration != null) {
            generations_ += newestGeneration!!
            newestGeneration = null
        } else
            throw IllegalStateException("no new generation to publish")
    }

    @Synchronized
    override fun discardNewGeneration() {
        if (newestGeneration != null) {
            newestGeneration!!.discard()
            newestGeneration = null
        } else
            throw IllegalStateException("no new generation to discard")

    }
}