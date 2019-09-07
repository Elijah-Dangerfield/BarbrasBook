package com.dangerfield.barbrasbook.view.articleDetail


import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.fragment_article_detail.*

/**
 * A simple [Fragment] subclass.
 */
class ArticleDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ib_article_back.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        var article = arguments?.getParcelable<Article>("KEY")
        iv_article_header.setColorFilter(
                ContextCompat.getColor(context!!,
                R.color.darkFilter),
                PorterDuff.Mode.SRC_ATOP
            )


        article?.let {

            tv_article_header.text = it.title
            tv_article_text.text = it.content
            tv_article_description.text = it.description
            tv_article_publisher.text = it.source.name.substringBefore(".")
            tv_article_date.text = it.publishedAt.toReadableDate()
            tv_article_author.text = (it.author ?: "Unknown Author")
            tv_article_link.text = it.url

            Glide
                .with(this)
                .load(it.urlToImage)
                .centerCrop()
                .into(iv_article_header)
        }

        btn_heart.setOnClickListener {
            confetti_animation.playAnimation()
            iv_heart.setImageDrawable(resources.getDrawable(R.drawable.ic_heart_filled, null))
            tv_heart.text = "Barbra loves you back"
        }

    }
}
