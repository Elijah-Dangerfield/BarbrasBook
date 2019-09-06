package com.dangerfield.barbrasbook.view.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dangerfield.barbrasbook.R
import com.dangerfield.barbrasbook.model.Article
import kotlinx.android.synthetic.main.item_article.view.*

class NewsAdapter(private val context: Context, list: ArrayList<Article>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var articles = ArrayList<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init { this.articles = list }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.tv_article_title
        val preview: TextView = view.tv_article_preview
        val image: ImageView = view.iv_article

        init {
            view.setOnClickListener {
                openDetails(it,adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article, parent, false))
    }

    override fun getItemCount() = articles.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.image.context)
            .load(articles[position].image)
            .centerCrop()
            .into(holder.image)

        //holder.image.background = context.getDrawable(R.drawable.ic_launcher_background)
        holder.title.text = articles[position].title
        holder.preview.text = "This is a super real article. Belive me. "
    }

    fun openDetails(view: View, position: Int) {
        val bundle = Bundle()
        val data = articles[position]
        bundle.putParcelable("KEY",data)
        Navigation.findNavController(view).navigate(R.id.action_newsFragment_to_articleDetailFragment,bundle)
    }
}