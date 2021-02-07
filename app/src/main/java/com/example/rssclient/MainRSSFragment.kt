package com.example.rssclient

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rssclient.databinding.Tab1FeedBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class MainRSSFragment : Fragment() {
    private lateinit var binding: Tab1FeedBinding
    private lateinit var newsList: MutableList<Item>
    private lateinit var adapter: ItemListFromMainChannelAdapter
    private lateinit var dialog: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog = AlertDialog.Builder(this.context)
        dialog.setMessage(resources.getString(R.string.InternetIssue))
        dialog.setPositiveButton("Еще раз") { _, _ ->
            createResponse()
        }
        dialog.create()

        newsList = (savedInstanceState?.getSerializable("newsList") as MutableList<Item>?)
            ?: mutableListOf()

        binding = DataBindingUtil.inflate(inflater, R.layout.tab_1_feed, container, false)
        adapter = ItemListFromMainChannelAdapter(this.context!!, newsList, activity as MainActivity)
        binding.mainRSSRecyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.mainRSSRecyclerView.adapter = adapter

        createResponse()

        return binding.root
    }

    fun createResponse() {
        NetworkService().api().getNewsList("RU")?.enqueue(object : Callback<RSSFeedResponse?> {
            override fun onResponse(
                call: Call<RSSFeedResponse?>,
                response: Response<RSSFeedResponse?>
            ) {
                Log.i("refresh","refresh")
                response.body()?.channel?.let { newsList.addAll(it.item) }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<RSSFeedResponse?>, t: Throwable) {
                dialog.show()
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable("newsList", newsList as Serializable)
        super.onSaveInstanceState(outState)
    }
}