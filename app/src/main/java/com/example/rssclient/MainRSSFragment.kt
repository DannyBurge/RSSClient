package com.example.rssclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssclient.databinding.Tab1FeedBinding

class MainRSSFragment : Fragment() {
    private lateinit var binding: Tab1FeedBinding
    private var newsList: MutableList<Item> = mutableListOf()
    private lateinit var adapter: ItemListFromMainChannelAdapter
    private lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainActivity = activity as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.tab_1_feed, container, false)

        // При инициализации RecyclerView и адаптера даем им пустой лист
        adapter = ItemListFromMainChannelAdapter(this.context!!, newsList, mainActivity)
        binding.mainRSSRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.mainRSSRecyclerView.adapter = adapter

        // Смотрим на изменение данных в постах
        (activity as MainActivity).getPostsList().observe(viewLifecycleOwner)
        {
            if (it != null) {
                newsList.clear()
                newsList.addAll(it)
            }
            // Обновляем отображение и рисуем анимацию
            adapter.notifyDataSetChanged()
            binding.mainRSSRecyclerView.scheduleLayoutAnimation()
        }

        return binding.root
    }
}