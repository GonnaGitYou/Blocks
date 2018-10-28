package com.example.drken.blocks.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EOSInfo {

    @SerializedName("server_version")
    @Expose
    var serverVersion: String? = null
    @SerializedName("head_block_num")
    @Expose
    var headBlockNum: Int? = null
    @SerializedName("last_irreversible_block_num")
    @Expose
    var lastIrreversibleBlockNum: Int? = null
    @SerializedName("head_block_id")
    @Expose
    var headBlockId: String? = null
    @SerializedName("head_block_time")
    @Expose
    var headBlockTime: String? = null
    @SerializedName("head_block_producer")
    @Expose
    var headBlockProducer: String? = null

}
