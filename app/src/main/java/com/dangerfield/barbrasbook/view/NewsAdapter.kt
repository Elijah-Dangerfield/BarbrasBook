package com.dangerfield.barbrasbook.view

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
import kotlinx.android.synthetic.main.item_article.view.*

class NewsAdapter(private val context: Context, list: ArrayList<String>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

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

        init {
            view.setOnClickListener {
                openDetails(it,adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return  ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article, parent, false))
    }

    override fun getItemCount() = articles.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.image.context)
            .load("https://www.thewrap.com/wp-content/uploads/2019/03/BarbraStreisand1.jpg")
            .into(holder.image)

        //holder.image.background = context.getDrawable(R.drawable.ic_launcher_background)
        holder.title.text = "This is a title"
        holder.preview.text = articles[position]
    }

    fun openDetails(view: View, position: Int) {
        val bundle = Bundle()
        val data = articles[position]
        bundle.putString("Title",data)
        Navigation.findNavController(view).navigate(R.id.action_newsFragment_to_articleDetailFragment,bundle)
    }
}