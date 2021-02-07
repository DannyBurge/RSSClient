package com.example.rssclient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RSSAPI {
    @GET("trends/trendingsearches/daily/rss")
fun getNewsList(@Query("geo") geo: String): Call<RSSFeedResponse?>?

}