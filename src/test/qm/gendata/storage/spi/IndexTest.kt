package qm.gendata.storage.spi

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import qm.gendata.storage.BlockList

internal class IndexTest {

    private lateinit var index: Index
    private lateinit var blocklist: BlockList

    @BeforeEach
    fun setUp() {
        blocklist = InMemoryBlocks()
        index = Index(blocklist)
    }

    @Test
    fun newIndexIsUpdating() {
        assertTrue(index.updating)
    }

    @Test
    fun newBlockForWriteThenRead() {
        var blockToUpdate = index.getBlock(0)
        assertTrue(blockToUpdate.updatable)
        index.stopUpdating()
        var blockToRead = index.getBlock(0)
        assertFalse(blockToRead.updatable)
        assertEquals(blockToRead, blockToUpdate)
    }

    @Test
    fun blockIsCopiedWhenUpdatingAgain() {
        var blockToUpdate = index.getBlock(0)
        assertEquals(blocklist.size - 1, blocklist.freeBlockCount)
        blockToUpdate[0] = "Test string"
        index.stopUpdating()
        index.updating = true
        var blockToUpdateAgain = index.getBlock(0)
        assertEquals(blocklist.size - 2, blocklist.freeBlockCount)
        assertTrue(blockToUpdateAgain.updatable)

    }
}