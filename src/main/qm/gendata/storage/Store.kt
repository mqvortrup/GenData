package qm.gendata.storage

import qm.gendata.storage.spi.GenerationImpl

class Store(val name: String) {

    val generations : List<Generation>
        get() = generations_

    private var generations_ = listOf<GenerationImpl>()
    private var newestGeneration : GenerationImpl? = null

    fun getGeneration(id: Int): Generation {
        if (id < 0 || id >= generations_.size)
            throw IllegalArgumentException("No such generation: $id in $name")
        else
            return generations_[id]
    }

    fun getLatestGeneration(): Generation {
        return getLastestGenerationImpl()
    }

    private fun getLastestGenerationImpl(): GenerationImpl {
        if (generations_.isEmpty())
            throw IllegalArgumentException("No latest generation in $name")
        else
            return generations_.last()
    }

    @Synchronized fun createNewGeneration(): ReadWriteGeneration {
        if (newestGeneration == null) {
            newestGeneration =
                    when (generations_.isEmpty()) {
                        true -> GenerationImpl()
                        false -> getLastestGenerationImpl().copy()
                    }
            return newestGeneration as ReadWriteGeneration
        } else
            throw IllegalStateException("a new generation already exists")
    }

    @Synchronized fun publishNewGeneration() {
        if (newestGeneration != null) {
            generations_ += newestGeneration!!
            newestGeneration = null
        } else
            throw IllegalStateException("no new generation to publish")
    }

    @Synchronized fun discardNewGeneration() {
        if (newestGeneration != null) {
            newestGeneration!!.discard()
            newestGeneration = null
        } else
            throw IllegalStateException("no new generation to discard")

    }
}


fun openStore(name: String): Store {
    return Store(name)
}