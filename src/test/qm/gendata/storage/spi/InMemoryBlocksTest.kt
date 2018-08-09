package qm.gendata.storage.spi

import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import qm.gendata.storage.BlockList
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

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

    @Test
    fun allocatedBlockIsUpdatable() {
        val allocatedBlock = blockList.allocate()
        val block = blockList[allocatedBlock]
        assertTrue(block.updatable)
        block.updatable = false
        assertThrows(AssertionError::class.java, { block[0] = "Test" } )
    }

    @Test
    fun copyingABlock() {
        val block1 = blockList[blockList.allocate()]
        val block2 = blockList[blockList.allocate()]
        block1[0] = "A test entry"
        block2.copyFrom(block1)
        assertEquals(block1[0], block2[0])
        block2[0] = "Another test entry"
        assertNotEquals(block1[0], block2[0])
        block1[0] = "Yet another test entry"
        assertNotEquals(block1[0], block2[0])
    }
}