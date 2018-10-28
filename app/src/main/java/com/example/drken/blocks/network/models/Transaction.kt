package com.example.drken.blocks.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Transaction {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("kcpu_usage")
    @Expose
    var kcpuUsage: Int? = null
    @SerializedName("net_usage_words")
    @Expose
    var netUsageWords: Int? = null
    @SerializedName("id")
    @Expose
    var id: String? = null

}
