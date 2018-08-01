package qm.gendata.storage

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import qm.gendata.storage.spi.SimpleStoreImpl

internal class NewStoreTest {

    var store : SimpleStoreImpl = openStore("test")

    @BeforeEach
    fun setUp() {
        store = openStore("test")
    }

    @Test
    fun newStoreHasNoGenerations() {
        assertEquals(0, store.generations.size)
        assertThrows(IllegalArgumentException::class.java, { store.getGeneration(0) })
        assertThrows(IllegalArgumentException::class.java, { store.getLatestGeneration() })
    }

    @Test
    fun createAndPublishNewGeneration() {
        val generation = store.createNewGeneration()
        assertNotNull(generation)
        store.publishNewGeneration()
        assertEquals(1, store.generations.size)
    }

    @Test
    fun createAndDiscardNewGeneration() {
        val generation = store.createNewGeneration()
        assertNotNull(generation)
        store.discardNewGeneration()
        assertEquals(0, store.generations.size)
    }

    @Test
    fun createAndCreateNewGenerationFails() {
        store.createNewGeneration()
        assertThrows(IllegalStateException::class.java, { store.createNewGeneration() })
    }

    @Test
    fun publishNewGenerationFails() {
        assertThrows(IllegalStateException::class.java, { store.publishNewGeneration() })
    }

    @Test
    fun getName() {
        assertEquals("test", store.name)
    }
}