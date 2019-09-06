package com.dangerfield.barbrasbook.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dangerfield.barbrasbook.R
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.android.synthetic.main.item_article.view.*

class NewsAdapter(val context: Context, list: ArrayList<String>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var articles = ArrayList<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init { this.articles = list }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.tv_article_title
        val preview: TextView = view.tv_article_preview
        val image: ImageView = view.iv_article
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article, parent, false))
    }

    override fun getItemCount() = articles.size


    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.image.background = context.getDrawable(R.drawable.ic_launcher_background)
        holder.title.text = "This is a title"
        holder.preview.text = "So is this"
    }
}