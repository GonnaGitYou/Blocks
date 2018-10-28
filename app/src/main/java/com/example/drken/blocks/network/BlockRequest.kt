package com.example.drken.blocks.network

import com.google.gson.annotations.SerializedName

class BlockRequest(@field:SerializedName("block_num_or_id")
                   internal val block_num_or_id: String)
