package com.dangerfield.barbrasbook.ui.articleFeed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dangerfield.barbrasbook.R
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.util.rotate
import com.dangerfield.barbrasbook.util.toReadableDate
import com.dangerfield.barbrasbook.model.ExpandedStatus

import kotlinx.android.synthetic.main.item_article.view.*

class NewsAdapter(private val context: Context): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var articles = listOf<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.tv_article_title
        val preview: TextView = view.tv_article_preview
        val image: ImageView = view.iv_article
        val source: TextView = view.tv_article_source
        val publishedDate: TextView = view.tv_article_published
        val btnExpand: ImageButton = view.ib_item_expand

        init { view.setOnClickListener { openDetails(it,adapterPosition) } }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_article, parent, false))
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = articles[position]

        holder.itemView.ib_item_expand.setOnClickListener { toggleExpansion(position) }

        handleExpansion(item,holder)

        bindView(item, holder)
    }

    private fun bindView(item: Article, holder: ViewHolder) {
        Glide.with(holder.image.context)
            .load(item.urlToImage)
            .placeholder(R.color.colorPrimary)
            .centerCrop()
            .into(holder.image)

        holder.title.text = item.title
        holder.preview.text = item.description
        holder.source.text = item.source.name.substringBefore(".")
        //drop first 4 for article preview, only show MM//DD
        holder.publishedDate.text = item.publishedAt?.toReadableDate()?.drop(4)
    }

    private fun handleExpansion(item: Article, holder: ViewHolder) {
        when(item.expandedStatus){
            ExpandedStatus.EXPANDED -> {expand(holder)}
            ExpandedStatus.COLLAPSED -> {collapse(holder)}
            else -> {
                holder.preview.maxLines = 2
                holder.btnExpand.rotation = 0f
            }
        }
    }

    private fun expand(holder: ViewHolder) {
        holder.preview.maxLines = Int.MAX_VALUE
        holder.btnExpand.rotate(0f,180f)
    }

    private fun collapse(holder: ViewHolder) {
        holder.preview.maxLines = 2
        holder.btnExpand.rotate(180f,0f)
    }

    private fun openDetails(view: View, position: Int) {
        val bundle = Bundle()
        val data = articles[position]
        bundle.putParcelable(context.resources.getString(R.string.article_key),data)
        Navigation.findNavController(view).navigate(R.id.action_newsFragment_to_articleDetailFragment,bundle)
    }

    private fun toggleExpansion(position: Int) {

        articles[position].expandedStatus = if(articles[position].expandedStatus == ExpandedStatus.EXPANDED)
            ExpandedStatus.COLLAPSED else ExpandedStatus.EXPANDED

        notifyItemChanged(position)
    }
}
