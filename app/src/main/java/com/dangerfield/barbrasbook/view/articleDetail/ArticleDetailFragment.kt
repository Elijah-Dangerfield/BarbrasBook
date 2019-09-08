package com.dangerfield.barbrasbook.view.articleDetail

import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.dangerfield.barbrasbook.R
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.util.toReadableDate
import kotlinx.android.synthetic.main.detail_content_layout.*
import kotlinx.android.synthetic.main.detail_content_layout.view.*
import kotlinx.android.synthetic.main.detail_header_layout.*
import kotlinx.android.synthetic.main.detail_header_layout.view.*

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

        ib_article_back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        btn_heart.setOnClickListener {playAnimation()}
    }

    fun playAnimation() {
        confetti_animation.playAnimation()
        iv_heart.background = resources.getDrawable(R.drawable.ic_heart_filled,null)
        tv_heart.text = getString(R.string.barba_loves_you)
    }

    fun initViewsWith(view: View, article: Article?) {
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
