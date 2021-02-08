package com.example.rssclient

import com.tickaroo.tikxml.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverter : TypeConverter<Date?> {
    private val formatterRead: SimpleDateFormat = SimpleDateFormat("E, dd MMM yyy HH:mm:ss Z", Locale.US) // Mon, 08 Feb 2021 01:00:00 +0300
    private val formatterWrite: SimpleDateFormat = SimpleDateFormat("dd.MM.yy", Locale.ROOT) // 08 Feb 2021

    @Throws(Exception::class)
    override fun read(value: String): Date? {
        return formatterRead.parse(value)
    }

    @Throws(Exception::class)
    override fun write(value: Date?): String? {
        return formatterWrite.format(value)
    }
}