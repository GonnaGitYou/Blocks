package com.example.drken.blocks.data

import com.example.drken.blocks.network.models.Block

import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class BlockContentTest {

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val block = Block()
        block.id = "x"
        block.producer = "A"
        block.producerSignature = "ABC"
        BlockContent.addItem(block)
    }

    @Test
    fun getOverviewDetails() {
        assertEquals("Verify the response when the block does not exist.",
                "Block ID Does Not Exist!",
                BlockContent.getOverviewDetails("AAAAAAAAA"))
        assertNotEquals("Verify we don't get the response when the block does exist.",
                "Block ID Does Not Exist!",
                BlockContent.getOverviewDetails("x"))
        assertEquals("Verify data input gives us expected data output.",
                "Producer: A\nTransaction Count: Unavailable\nProducer Signature: ABC",
                BlockContent.getOverviewDetails("x"))
    }

    @Test
    fun getRawDetails() {
        assertEquals("Verify the response when the block does not exist.",
                "Block ID Does Not Exist!",
                BlockContent.getOverviewDetails("AAAAAAAAA"))
    }
}