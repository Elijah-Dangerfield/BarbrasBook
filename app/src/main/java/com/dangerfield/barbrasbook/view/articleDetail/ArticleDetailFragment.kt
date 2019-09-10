package com.dangerfield.barbrasbook.view.articleDetail

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dangerfield.barbrasbook.R
import com.dangerfield.barbrasbook.db.ArticlesDatabase
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.util.toReadableDate
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.detail_content_layout.*
import kotlinx.android.synthetic.main.detail_content_layout.view.*
import kotlinx.android.synthetic.main.detail_header_layout.*
import kotlinx.android.synthetic.main.detail_header_layout.view.*
import kotlinx.android.synthetic.main.fragment_article_detail.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.math.abs


class ArticleDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article_detail, container, false)
        val article = arguments?.getParcelable<Article>(context?.resources?.getString(R.string.article_key))
        initViewsWith(view, article)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configureToolBar()
        btn_heart.setOnClickListener {playAnimation()}
    }

    private fun configureToolBar() {
        (activity as AppCompatActivity).setSupportActionBar(anim_toolbar)
        anim_toolbar.title = ""
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, offSet ->
            //offset: 0 means fully expanded
            if(abs(offSet) > 200) {
                setTitle(true)
            }else{
                setTitle(false)
            }
        })
    }

    private fun setTitle(needsShown: Boolean) {
        //only set the title if it needs set
        if(needsShown && collapsing_toolbar?.title.isNullOrEmpty()){
            collapsing_toolbar?.title = tv_article_publisher.text
        }else if (!needsShown && collapsing_toolbar.title != ""){
            collapsing_toolbar?.title = ""
        }
    }

    private fun playAnimation() {
        confetti_animation.playAnimation()
        iv_heart.background = resources.getDrawable(R.drawable.ic_heart_filled,null)
        tv_heart.text = getString(R.string.barba_loves_you)
    }

    private fun initViewsWith(view: View, article: Article?) {
        view.run {
            iv_article_header.setColorFilter(
                ContextCompat.getColor(context!!,
                    R.color.darkFilter),
                PorterDuff.Mode.SRC_ATOP
            )

            article?.let {
                tv_article_text.text = it.content
                tv_article_description.text = it.description
                tv_article_publisher.text = it.source.name.substringBefore(".")
                tv_article_date.text = it.publishedAt.toReadableDate()
                tv_article_author.text = (it.author ?: "Unknown Author")
                tv_article_link.text = it.url
                tv_article_header.text = it.title

                Glide
                    .with(this)
                    .load(it.urlToImage)
                    .placeholder(R.color.colorPrimary)
                    .centerCrop()
                    .into(iv_article_header)
            }
        }
    }
}
