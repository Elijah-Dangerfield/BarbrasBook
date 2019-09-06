package com.dangerfield.barbrasbook.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dangerfield.barbrasbook.R
import com.dangerfield.barbrasbook.model.Article
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var article = arguments?.getParcelable<Article>("KEY")

        article?.let {
            Glide.with(this).load(it.image).into(iv_article_header)
        }


    }

}
