package com.example.rssclient

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class ItemListFromMainChannelAdapter(
    var context: Context,
    private val items: List<Item>?,
    val activity: MainActivity
) :
    RecyclerView.Adapter<ItemListFromMainChannelAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    var listWasChanged = activity.getSavedListSize()
    var savedPostsList = activity.getSavedPostsList()

    // Создаем элемент списка который отображается на экране
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.item_from_main_channel_item_list, parent, false)
        return ViewHolder(view)
    }

    // Задаем значения для элемента списка
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.get(position)?.let { holder.bind(it) }
        Picasso.get()
            .load(items?.get(position)?.picture)
            .fit()
            .into(holder.picture)
    }

    // Получаем количество элементов в списке
    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        val picture: ImageView = view.findViewById(R.id.itemFromChannelPicture)
        private val title: TextView = view.findViewById(R.id.itemFromChanelTitle)
        private val recyclerView: RecyclerView =
            view.findViewById(R.id.recyclerViewForNewsItemsList)
        private val button: Button = view.findViewById(R.id.itemFromChanelButton)

        fun bind(item: Item) {
            // Увеличим область нажатия кнопули
            val parent = button.parent as View
            parent.post {
                val rect = Rect()
                button.getHitRect(rect)
                rect.top -= 100
                rect.left -= 100
                rect.bottom += 100
                rect.right += 100
                parent.touchDelegate = TouchDelegate(rect, button)
            }

            button.setOnClickListener {
                if (activity.getSavedListSize().value == null) {
                    activity.setSavedPostsList(mutableListOf(item))
                    activity.setSavedListSize(1)
                    Toast.makeText(context, "Добавлено в сохраненные", Toast.LENGTH_SHORT).show()
                } else {
                    if (!activity.getSavedPostsList().value?.contains(item)!!) {
                        activity.getSavedPostsList().value?.add(item)
                        activity.setSavedListSize(activity.getSavedPostsList().value!!.size)
                        Toast.makeText(context, "Добавлено в сохраненные", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Уже в сохраненных", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            title.text = item.title
            recyclerView.layoutManager = LinearLayoutManager(context)
            val itemList: MutableList<NewsItem> = mutableListOf()
            val adapter = ItemListFromItemAdapter(context, itemList)
            recyclerView.adapter = adapter
            itemList.addAll(item.newsItemList)
            adapter.notifyDataSetChanged()
        }
    }
}