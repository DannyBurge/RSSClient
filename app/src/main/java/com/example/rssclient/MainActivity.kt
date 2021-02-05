package com.example.rssclient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.rssclient.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    val url = "https://lenta.ru/"

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val adapter = FragmentTabAdapter(supportFragmentManager, this)
        adapter.addFragment(MainRSSFragment())
        adapter.addFragment(LaterRSSFragment())

        binding.viewPagerMainActivity.adapter = adapter
        binding.viewPagerMainActivity.currentItem = savedInstanceState?.getInt("activeTab") ?: 0
        binding.tabsMainActivity.setupWithViewPager(binding.viewPagerMainActivity)
//        setRollResultHistory(savedInstanceState?.getSerializable("rollHistory") as MutableList<RollResult>?)
//        savedInstanceState?.clear()

    }
}