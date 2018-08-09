package qm.gendata.storage.spi

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import qm.gendata.storage.BlockList

internal class InMemoryBlocksTest {

    private lateinit var blockList: BlockList
    val size = 10

    @BeforeEach
    fun setUp() {
        blockList = InMemoryBlocks(size, 10)
    }

    @Test
    fun allBlocksAreFreeAndUnreferencedInNewBlockList() {
        assertEquals(size, blockList.freeBlockCount)
        for (id in 0..size-1) assertEquals(0, blockList[id].references)
    }

    @Test
    fun allocatedBlockHasSingleReference() {
        val allocatedBlock = blockList.allocate()
        assertEquals(size-1, blockList.freeBlockCount)
        assertEquals(1, blockList[allocatedBlock].references)
    }

    @Test
    fun freedBlockHasNoReferences() {
        val allocatedBlock = blockList.allocate()
        blockList.free(allocatedBlock)
        assertEquals(size, blockList.freeBlockCount)
        assertEquals(0, blockList[allocatedBlock].references)
    }
}