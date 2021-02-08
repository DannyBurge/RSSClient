package com.example.rssclient

import android.app.AlertDialog
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var dialog: AlertDialog.Builder

    // Объявляем LiveData переменные с сеттерами и геттерами
    // для обмена данными между фрагментами и активити, а так же для Observer'ов
    private var savedPostsList = MutableLiveData<MutableList<Item>?>()
    fun getSavedPostsList(): LiveData<MutableList<Item>?> {
        return savedPostsList
    }

    fun setSavedPostsList(mutList: MutableList<Item>?) {
        if (mutList != null) {
            savedPostsList.value = mutList
        }
    }

    private var postsList = MutableLiveData<List<Item>?>()
    fun getPostsList(): LiveData<List<Item>?> {
        return postsList
    }

    fun setPostsList(mutList: List<Item>) {
        postsList.value = mutList as MutableList<Item>?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedPostsList.value = mutableListOf()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Диалог-заглушка для плохого интернета (или отсутствия)
        dialog = AlertDialog.Builder(this)
        dialog.setMessage(resources.getString(R.string.InternetIssue))
        dialog.setPositiveButton("Еще раз") { _, _ ->
            createResponse()
        }
        dialog.setNeutralButton("Перейти в сохраненные") { _, _ ->
            binding.viewPagerMainActivity.currentItem = 1
        }
        dialog.create()

        // Добываем сохраненные записи при включении из ПЗУ
        val prefs = getSharedPreferences("savedPostsList", Context.MODE_PRIVATE)
        val json: Set<String>? = prefs.getStringSet("savedPostsList", mutableSetOf())
        val savedTmp: MutableList<Item> = mutableListOf()
        for (jsonStr in json!!) {
            savedTmp.add(Gson().fromJson(jsonStr, Item::class.java))
        }
        setSavedPostsList(savedTmp)

        // Навешиваем фрагменты на адаптер, а так же на листалку
        val adapter = FragmentTabAdapter(supportFragmentManager, this)

        adapter.addFragment(MainRSSFragment())
        adapter.addFragment(LaterRSSFragment())

        binding.viewPagerMainActivity.adapter = adapter
        binding.viewPagerMainActivity.currentItem = savedInstanceState?.getInt("activeTab") ?: 0
        binding.tabsMainActivity.setupWithViewPager(binding.viewPagerMainActivity)

        // Проверим, новый запуск или изменение конфигурации
        if (savedInstanceState != null) {
            setPostsList(savedInstanceState.getSerializable("newList") as List<Item>)
        } else createResponse()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Навешиваем на кнопку меню обновление списка
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshButton -> {
                createResponse()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        // На случай смены конфигурации сохраняемся
        outState.putInt("activeTab", binding.viewPagerMainActivity.currentItem)
        outState.putSerializable("newList", postsList.value as Serializable?)

        // Выкидываем список сохраненных записей в ПЗУ
        val prefs = getSharedPreferences("savedPostsList", Context.MODE_PRIVATE)
        val stringSet: MutableSet<String> = mutableSetOf()
        if (getSavedPostsList().value != null) {
            for (savedPost in getSavedPostsList().value!!) {
                stringSet.add(Gson().toJson(savedPost))
            }
            prefs.edit().putStringSet("savedPostsList", stringSet).apply()
        }
        super.onSaveInstanceState(outState)
    }

    // Создаем запрос на получение данных
    private fun createResponse() {
        NetworkService().api().getNewsList("RU")?.enqueue(object : Callback<RSSFeedResponse?> {
            // Удача
            override fun onResponse(
                call: Call<RSSFeedResponse?>,
                response: Response<RSSFeedResponse?>
            ) {
                // Если сервер ответил правильно, то кладем полученный список постов в LiveData
                if (response.code() == 200) {
                    response.body()?.channel?.let { setPostsList(it.item) }
                } else dialog.setMessage(R.string.ServerIssue).show()

            }

            // Неудача, показываем диалог
            override fun onFailure(call: Call<RSSFeedResponse?>, t: Throwable) {
                dialog.show()
            }

        })
    }
}