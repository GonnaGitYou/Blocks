package com.example.drken.blocks.network

import com.example.drken.blocks.network.models.Block
import com.example.drken.blocks.network.models.EOSInfo

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.POST

interface EOSInterface {

    @get:POST("v1/chain/get_info")
    val info: Call<EOSInfo>

    @POST("v1/chain/get_block")
    fun getBlock(@Body body: BlockRequest): Call<Block>

}
