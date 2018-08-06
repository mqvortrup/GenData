package qm.gendata.storage

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class NewStoreTest {

    private lateinit var store : Store

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
    fun deleteFailOnEmptyStore() {
        assertThrows(IllegalArgumentException::class.java, { store.deleteGeneration(0) })
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
        val newGeneration = store.createNewGeneration()
        assertNotNull(newGeneration)
        store.discardNewGeneration()
        assertThrows(AssertionError::class.java, { newGeneration.length() })
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
    fun deleteAGeneration() {
        val newGeneration = store.createNewGeneration()
        store.publishNewGeneration()
        store.deleteGeneration(newGeneration.id)
        assertThrows(AssertionError::class.java, { newGeneration.length() })
        assertEquals(0, store.generations.size)
    }

    @Test
    fun getName() {
        assertEquals("test", store.name)
    }
}