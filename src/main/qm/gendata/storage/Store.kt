package qm.gendata.storage

import qm.gendata.storage.spi.SimpleStoreImpl

interface Store {
    val name: String
    val generations : List<Generation>

    fun getGeneration(id: Int): Generation

    fun getLatestGeneration(): Generation

    fun deleteGeneration(id: Int)

    fun createNewGeneration(): ReadWriteGeneration

    fun publishNewGeneration()

    fun discardNewGeneration()
}


fun openStore(name: String): Store {
    return SimpleStoreImpl(name)
}