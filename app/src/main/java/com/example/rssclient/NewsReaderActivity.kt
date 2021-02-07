package com.example.rssclient

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.rssclient.databinding.ActivityNewsReaderBinding

class NewsReaderActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewsReaderBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_reader)
        val url: String = intent.getStringExtra("newsLink")!!

        binding.newsReaderWebView.settings.javaScriptEnabled = true
        binding.newsReaderWebView.loadUrl(url)


    }
}