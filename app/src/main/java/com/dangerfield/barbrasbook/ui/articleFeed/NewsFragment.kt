package com.dangerfield.barbrasbook.ui.articleFeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dangerfield.barbrasbook.R
import com.dangerfield.barbrasbook.api.Resource
import com.dangerfield.barbrasbook.util.showIf
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {

    private var adapter: NewsAdapter? = null
    //gets view model will @inject dependencies
    private val newsViewModel : NewsViewModel by  viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
        = inflater.inflate(R.layout.fragment_news, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureArticles()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //used to help with the #sick collapsable header
        collapsing_toolbar.post { collapsing_toolbar.requestLayout() }
        setupRefresher()
        setupViewModel()
    }

    private fun setupRefresher() {
        swipe_refresh_layout.setColorSchemeResources( R.color.colorPrimary, android.R.color.holo_blue_light
            , android.R.color.holo_blue_dark)

        swipe_refresh_layout.setOnRefreshListener { newsViewModel.refreshArticles() }
    }

    private fun setupViewModel() {

        newsViewModel.getLatestArticles().observe(viewLifecycleOwner, Observer {response ->
            pb_latest.showIf(response is Resource.Loading)
            swipe_refresh_layout.isRefreshing = (response is Resource.Loading && response.refreshing)
            when(response) {
                is Resource.Success -> adapter?.articles = response.data ?: listOf()
                is Resource.Error -> Toast.makeText(context, response.message ,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun configureArticles() {
        rv_articles.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(context!!)
        rv_articles.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        //cancel any outstanding jobs in repository
        newsViewModel.cancelJobs()
    }
}
