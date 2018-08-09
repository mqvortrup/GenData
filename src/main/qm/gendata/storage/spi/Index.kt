package qm.gendata.storage.spi

import qm.gendata.storage.Block
import qm.gendata.storage.BlockList

class Index(private val blockList: BlockList) {

    var updating = true
        private set

    private val blockMap = mutableMapOf<Int, Int>()

    fun getBlock(id: Int): Block {
        if (updating) {
            return getBlockForReadWrite(id)
        } else {
            return getBlockForReadOnly(id)
        }
    }

    private fun getBlockForReadOnly(id: Int): Block {
        val mappedId = blockMap.get(id)
        return when (mappedId) {
            null -> throw IllegalArgumentException("Block with id $id does not exist")
            else -> blockList[mappedId]
        }

    }

    private fun getBlockForReadWrite(id: Int): Block {
        val mappedId = blockMap.get(id)
        return when (mappedId) {
            null -> {
                val newMappedId = blockList.allocate()
                blockMap.put(id, newMappedId)
                val block = blockList[newMappedId]
                block.updating = true
                return block
           }
            else -> {
                when (blockList[mappedId].updating) {
                    true -> blockList[mappedId]
                    false -> {
                        val newMappedId = blockList.allocate()
                        blockMap.put(id, newMappedId)
                        val oldBlock = blockList[mappedId]
                        val newBlock = blockList[newMappedId]
                        newBlock.copyFrom(oldBlock)
                        newBlock.updating = true
                        return newBlock
                    }
                }
            }
        }
    }

    fun stopUpdating() {
        updating = false
    }
}