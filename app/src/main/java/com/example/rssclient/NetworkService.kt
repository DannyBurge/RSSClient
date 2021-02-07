package com.example.rssclient

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit

class NetworkService() {
    private var tikXml: TikXml = TikXml.Builder()
    .exceptionOnUnreadXml(false)
    .build()

    private var builder: Retrofit = Retrofit.Builder()
        .baseUrl("https://trends.google.com/")
        .addConverterFactory(TikXmlConverterFactory.create(tikXml))
        .build()
    private var rssAPI: RSSAPI =
        builder.create(RSSAPI::class.java)

    fun api(): RSSAPI {
        return rssAPI
    }
}