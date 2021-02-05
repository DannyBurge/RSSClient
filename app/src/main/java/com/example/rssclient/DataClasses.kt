package com.example.rssclient

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml
data class Response(
    @Element val rss: Rss
)

@Xml
data class Rss(
    @Element val channel: Channel
)

@Xml
data class Channel (
    
        )


@Xml
data class item(
    @Element val author: String,
    @Element val title: String,
    @Element val link: String,
    @Element val description: String,
    @Element val pubDate: String,
    @Element val enclosure: String,
    @Element val category: String,
)