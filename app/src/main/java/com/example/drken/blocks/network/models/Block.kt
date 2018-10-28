package com.example.drken.blocks.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Block {

    @SerializedName("previous")
    @Expose
    var previous: String? = null
    @SerializedName("timestamp")
    @Expose
    var timestamp: String? = null
    @SerializedName("transaction_mroot")
    @Expose
    var transactionMroot: String? = null
    @SerializedName("action_mroot")
    @Expose
    var actionMroot: String? = null
    @SerializedName("block_mroot")
    @Expose
    var blockMroot: String? = null
    @SerializedName("producer")
    @Expose
    var producer: String? = null
    @SerializedName("schedule_version")
    @Expose
    var scheduleVersion: Int? = null
    @SerializedName("new_producers")
    @Expose
    var newProducers: Any? = null
    @SerializedName("producer_signature")
    @Expose
    var producerSignature: String? = null
    @SerializedName("regions")
    @Expose
    var regions: List<Region>? = null
    @SerializedName("input_transactions")
    @Expose
    var inputTransactions: List<Any>? = null
    @SerializedName("id")
    @Expose
    lateinit var id: String
    @SerializedName("block_num")
    @Expose
    var blockNum: Int? = null
    @SerializedName("ref_block_prefix")
    @Expose
    var refBlockPrefix: Int? = null

}
