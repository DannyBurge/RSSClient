package com.example.rssclient

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssclient.databinding.Tab2SavedBinding
import com.google.gson.Gson


class LaterRSSFragment : Fragment() {
    private lateinit var binding: Tab2SavedBinding
    private lateinit var savedPostsList: MutableList<Item>
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mainActivity = activity as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.tab_2_saved, container, false)

        savedPostsList = mainActivity.getSavedPostsList().value ?: mutableListOf()
        val adapter = SavedItemListAdapter(this.context!!, savedPostsList)
        binding.savedRSSRecyclerView.adapter = adapter
        binding.savedRSSRecyclerView.layoutManager = LinearLayoutManager(this.context)

        mainActivity.getSavedListSize().observe(viewLifecycleOwner)
        {
            mainActivity.setSavedPostsList(savedPostsList)
            binding.savedRSSRecyclerView.adapter = SavedItemListAdapter(this.context!!, savedPostsList)
        }

        return binding.root
    }

}
