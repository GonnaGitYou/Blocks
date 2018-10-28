package com.example.drken.blocks.data

import com.example.drken.blocks.network.models.Block
import com.google.gson.Gson

import java.util.ArrayList
import java.util.HashMap

/**
 * Class to persist and format block data.
 */
object BlockContent {

    val ITEMS: MutableList<Block> = ArrayList()
    val ITEM_MAP: MutableMap<String, Block> = HashMap()

    fun addItem(item: Block) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    fun getOverviewDetails(id: String): String {
        val block = ITEM_MAP[id]
        if (block != null) {
            val builder = StringBuilder()
            if (block.producer != null) {
                builder.append("Producer: ").append(block.producer)
            } else {
                builder.append("Producer: ").append("Unavailable")
            }
            if (block.inputTransactions != null) {
                builder.append("\nTransaction Count: ").append(block.inputTransactions!!.size)
            } else {
                builder.append("\nTransaction Count: ").append("Unavailable")
            }
            if (block.producerSignature != null) {
                builder.append("\nProducer Signature: ").append(block.producerSignature)
            } else {
                builder.append("\nProducer Signature: ").append("Unavailable")
            }
            return builder.toString()
        } else {
            return "Block ID Does Not Exist!"
        }
    }

    fun getRawDetails(id: String): String {
        val block = ITEM_MAP[id]
        if (block != null) {
            val gson = Gson()
            val rawData = gson.toJson(block)
            val builder = StringBuilder()
            builder.append("Raw Data: ").append(rawData)
            return builder.toString()
        } else {
            return "Block ID Does Not Exist!"
        }
    }


}
