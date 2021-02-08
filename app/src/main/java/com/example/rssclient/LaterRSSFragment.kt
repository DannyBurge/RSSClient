package com.example.rssclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssclient.databinding.Tab2SavedBinding


class LaterRSSFragment : Fragment() {
    private lateinit var binding: Tab2SavedBinding
    private var savedPostsList: MutableList<Item> = mutableListOf()
    private lateinit var adapter: SavedItemListAdapter
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mainActivity = activity as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.tab_2_saved, container, false)

        // При инициализации RecyclerView и адаптера даем им пустой лист
        adapter = SavedItemListAdapter(this.context!!, savedPostsList, mainActivity)
        binding.savedRSSRecyclerView.adapter = adapter
        binding.savedRSSRecyclerView.layoutManager = LinearLayoutManager(this.context)

        // Смотрим на изменение данных в сохраненных постах
        mainActivity.getSavedPostsList().observe(viewLifecycleOwner)
        {
            if (it != null) {
                if (it.isNotEmpty()) {
                    savedPostsList.clear()
                    savedPostsList.addAll(it)
                }
            }
            // Обновляем отображение
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

}
