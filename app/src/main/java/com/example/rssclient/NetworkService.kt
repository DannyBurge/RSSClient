package com.example.rssclient

import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit

class NetworkService(url: String) {
    private var builder: Retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(TikXmlConverterFactory.create())
        .build()
    private var rssAPI: RSSAPI =
        builder.create(RSSAPI::class.java)

    fun api(): RSSAPI {
        return rssAPI
    }
}