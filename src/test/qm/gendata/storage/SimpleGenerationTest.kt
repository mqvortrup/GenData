package qm.gendata.storage

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SimpleGenerationTest {

    private lateinit var store : Store

    @BeforeEach
    fun setUp() {
        store = openStore("test")
        val newGeneration = store.createNewGeneration()
        for (i in 0..99) newGeneration[i] = "String $i"
        store.publishNewGeneration()
    }

    @Test
    fun newStoreHasOneGeneration() {
        assertEquals(1, store.generations.size)
        val generation = store.getGeneration(0)
        hasOriginalContent(generation)
    }

    @Test
    fun newGenerationHasSameContent() {
        try {
            val newGeneration = store.createNewGeneration()
            hasOriginalContent(newGeneration)
        } finally {
            store.discardNewGeneration()
        }
    }

    fun hasOriginalContent(generation: Generation) {
        for (i in 0..99) assertEquals("String $i", generation.get(i))
    }

    @Test
    fun updatingNewGenerationDoesntChangeOld() {
        val newGeneration = store.createNewGeneration()
        newGeneration.set(49, "New String 49")
        assertEquals("String 48", newGeneration[48])
        assertEquals("String 50", newGeneration[50])
        assertEquals("New String 49", newGeneration[49])
        store.publishNewGeneration()
        assertEquals("String 49", store.getGeneration(0)[49])
    }
}