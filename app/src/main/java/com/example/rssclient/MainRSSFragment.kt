package com.example.rssclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.rssclient.databinding.Tab1FeedBinding

class MainRSSFragment : Fragment() {
    private lateinit var binding: Tab1FeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val url = (activity as MainActivity).url
        binding = DataBindingUtil.inflate(inflater, R.layout.tab_1_feed, container, false)

//        NetworkService(url).api().getNewsList()?.enqueue(object : Callback<List<Post?>?> {
//            override fun onResponse(call: Call<List<Post?>?>, response: Response<List<Post?>?>) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onFailure(call: Call<List<Post?>?>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })

        return binding.root
    }
}