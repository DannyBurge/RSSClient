package com.example.rssclient

import retrofit2.Call
import retrofit2.http.GET

interface RSSAPI {
    @GET("rss/news")
    fun getNewsList(): Call<List<Post?>?>?
}