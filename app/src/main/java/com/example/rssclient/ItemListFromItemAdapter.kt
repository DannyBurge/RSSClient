package com.example.rssclient

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.text.Html
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ItemListFromItemAdapter(
    var context: Context,
    private val items: List<NewsItem>?,
) :
    RecyclerView.Adapter<ItemListFromItemAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    // Создаем элемент списка который отображается на экране
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.news_item_list, parent, false)
        return ViewHolder(view)
    }

    // Задаем значения для элемента списка
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items?.get(position)?.let { holder.bind(it) }
    }

    // Получаем количество элементов в списке
    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        private val sourceText: TextView = view.findViewById(R.id.newsItemSource)
        private val titleText: TextView = view.findViewById(R.id.newsItemTitle)
        private val snippetText: TextView = view.findViewById(R.id.newsItemSnippet)
        private val buttonGoTo: Button = view.findViewById(R.id.buttonToNew)

        // Фильтруем строку на интернет-символы
        private fun replaceBadSymbols(str: String): String {
            return Html.fromHtml(str, Html.FROM_HTML_MODE_LEGACY).toString()
                .replace("&nbsp;", "")
                .replace("&quot;", "\"")
                .replace("&#39;", "\'")
                .replace("&amp;", "&")
        }

        fun bind(newsItem: NewsItem) {
            sourceText.text = newsItem.news_item_source.toUpperCase(Locale.ROOT)
            titleText.text = replaceBadSymbols(newsItem.news_item_title)
            snippetText.text = replaceBadSymbols(newsItem.news_item_snippet)

            // Увеличим область нажатия кнопули
            val parent = buttonGoTo.parent as View
            parent.post {
                val rect = Rect()
                buttonGoTo.getHitRect(rect)
                rect.top -= 100
                rect.left -= 100
                rect.bottom += 100
                rect.right += 100
                parent.touchDelegate = TouchDelegate(rect, buttonGoTo)
            }

            // На нажатие кнопы открывается второе активити с WebView
            // и по ссылке из объекта открывает новость
            buttonGoTo.setOnClickListener {
                val intent = Intent(context, NewsReaderActivity::class.java)
                intent.putExtra("newsLink", newsItem.news_item_url)
                context.startActivity(intent)
            }
        }
    }
}