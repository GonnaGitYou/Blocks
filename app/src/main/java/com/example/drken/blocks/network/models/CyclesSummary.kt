package com.example.drken.blocks.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CyclesSummary {

    @SerializedName("read_locks")
    @Expose
    var readLocks: List<Any>? = null
    @SerializedName("write_locks")
    @Expose
    var writeLocks: List<Any>? = null
    @SerializedName("transactions")
    @Expose
    var transactions: List<Transaction>? = null

}
