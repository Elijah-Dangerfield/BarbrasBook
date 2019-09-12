package com.dangerfield.barbrasbook.view.latest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.dangerfield.barbrasbook.R
import com.dangerfield.barbrasbook.api.LoadingStatus
import com.dangerfield.barbrasbook.util.showIf
import com.dangerfield.barbrasbook.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {

    private var adapter: NewsAdapter? = null
    //gets view model will @inject dependencies
    private val mainViewModel : MainViewModel by  viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
        = inflater.inflate(R.layout.fragment_news, container, false)

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

        swipe_refresh_layout.setOnRefreshListener { mainViewModel.refreshArticles() }
    }

    private fun setupViewModel() {

        mainViewModel.getLatestArticles().observe(viewLifecycleOwner, Observer {articles ->
            adapter?.articles = articles
            if(articles.isNotEmpty()) swipe_refresh_layout.isRefreshing = false
        })

        mainViewModel.getArticleLoadingStatus().observe(viewLifecycleOwner, Observer {loadingStatus ->
            pb_latest.showIf(loadingStatus == LoadingStatus.LOADING)

            //then the API request failed AND we could not load from DB
            tv_loading_error.showIf(loadingStatus == LoadingStatus.FAILED
                    && adapter?.articles.isNullOrEmpty())

            //API request failed but we could laod from DB :)
            if(loadingStatus == LoadingStatus.FAILED && adapter?.articles?.isNotEmpty() == true){
                //then user tried to get data but could not
                Toast.makeText(context,
                    resources.getString(R.string.api_failed),
                    Toast.LENGTH_LONG)
                    .show()
            }
            swipe_refresh_layout.isRefreshing = loadingStatus == LoadingStatus.REFRESHING
        })
    }

    private fun configureArticles() {
        rv_articles.layoutManager = LinearLayoutManager(context)
        adapter = NewsAdapter(context!!, ArrayList())
        rv_articles.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        //cancel any outstanding jobs in repository
        mainViewModel.cancelJobs()
    }
}
