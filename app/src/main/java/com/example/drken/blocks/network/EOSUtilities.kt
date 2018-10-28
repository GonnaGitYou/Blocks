package com.example.drken.blocks.network

object EOSUtilities {
    private val BASE_URL = "https://api.eosnewyork.io/"

    val eosInterface: EOSInterface
        get() = RetrofitClientGenerator.getClient(BASE_URL)!!.create(EOSInterface::class.java!!)
}
