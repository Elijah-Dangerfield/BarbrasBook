package com.dangerfield.barbrasbook.view.news


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dangerfield.barbrasbook.R
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_news.*


class NewsFragment : Fragment() {

    private var adapter: NewsAdapter? = null
    lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureArticles()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)

        viewModel.getLatestArticles().observe(viewLifecycleOwner, Observer {articles ->
            adapter?.articles = articles
        })



    }

    fun configureArticles() {
        rv_articles.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(context!!, ArrayList())
        rv_articles.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelJobs()
    }
}
