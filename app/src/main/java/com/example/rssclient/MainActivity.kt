package com.example.rssclient

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rssclient.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainRSSFragment: MainRSSFragment

    private var savedPostsList = MutableLiveData<MutableList<Item>?>()
    fun getSavedPostsList(): LiveData<MutableList<Item>?> {
        return savedPostsList
    }

    fun setSavedPostsList(mutList: MutableList<Item>?) {
        if (mutList != null) {
            savedPostsList.value = mutList
        }
    }

    private val savedListSize = MutableLiveData<Int>()
    fun getSavedListSize(): LiveData<Int> {
        return savedListSize
    }

    fun setSavedListSize(size: Int) {
        savedListSize.value = size
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedPostsList.value = mutableListOf()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val prefs = getSharedPreferences("savedPostsList", Context.MODE_PRIVATE)
        val json: Set<String>? = prefs.getStringSet("savedPostsList", mutableSetOf())

        if (json != null) {
            for (jsonStr in json) {
                savedPostsList.value?.add(Gson().fromJson(jsonStr, Item::class.java))
                savedPostsList.value?.size?.let {
                    setSavedListSize(it)
                }
            }
        }

        val adapter = FragmentTabAdapter(supportFragmentManager, this)
        mainRSSFragment = MainRSSFragment()

        adapter.addFragment(mainRSSFragment)
        adapter.addFragment(LaterRSSFragment())

        binding.viewPagerMainActivity.adapter = adapter
        binding.viewPagerMainActivity.currentItem = savedInstanceState?.getInt("activeTab") ?: 0
        binding.tabsMainActivity.setupWithViewPager(binding.viewPagerMainActivity)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshButton -> mainRSSFragment.createResponse()
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("activeTab", binding.viewPagerMainActivity.currentItem)
        val prefs = getSharedPreferences("savedPostsList", Context.MODE_PRIVATE)
        val stringSet: MutableSet<String> = mutableSetOf()
        if (savedPostsList.value != null) {
            for (savedPost in savedPostsList.value!!) {
                stringSet.add(Gson().toJson(savedPost))
            }
            prefs.edit().putStringSet("savedPostsList", stringSet).apply()
        }
        super.onSaveInstanceState(outState)
    }
}