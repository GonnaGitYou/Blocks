package com.example.drken.blocks.network.models

import com.example.drken.blocks.network.models.CyclesSummary
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Region {

    @SerializedName("region")
    @Expose
    var region: Int? = null
    @SerializedName("cycles_summary")
    @Expose
    var cyclesSummary: List<List<CyclesSummary>>? = null

}
