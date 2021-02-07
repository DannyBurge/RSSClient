package com.example.rssclient

import android.os.Parcelable
import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import kotlinx.android.parcel.Parcelize

@Xml
data class RSSFeedResponse(
    @Attribute val version: String,
    @Element val channel: ChannelRSS
)

@Xml(name = "channel")
data class ChannelRSS(
    @PropertyElement val title: String,
    @Element val item: List<Item>,
)

@Parcelize
@Xml(name = "item")
data class Item(
    @PropertyElement val title: String,
    @PropertyElement(name = "ht:picture") val picture: String,
    @PropertyElement val pubDate: String,
    @Element val newsItemList: List<NewsItem>
) : Parcelable

@Parcelize
@Xml(name = "ht:news_item")
data class NewsItem(
    @PropertyElement(name = "ht:news_item_title") val news_item_title: String,
    @PropertyElement(name = "ht:news_item_snippet") val news_item_snippet: String,
    @PropertyElement(name = "ht:news_item_url") val news_item_url: String,
    @PropertyElement(name = "ht:news_item_source") val news_item_source: String,
) : Parcelable
