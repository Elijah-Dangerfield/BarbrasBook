package com.dangerfield.barbrasbook.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.networking.LoadingStatus
import com.dangerfield.barbrasbook.networking.Repository

class MainViewModel: ViewModel() {

    var searchTerm: String? = null
    private var latestArticles = MutableLiveData<List<Article>>()
    private var articleLoadingStatus = MutableLiveData<LoadingStatus>()

    fun cancelJobs() { Repository.cancelJobs() }

    fun refreshArticles() {
        //force pull when user wants refresh
        latestArticles = Repository.getLatest(searchTerm,refreshing = true)
    }

    fun getArticleLoadingStatus(): LiveData<LoadingStatus> {
        articleLoadingStatus = Repository.getArticleLoadingStatus()
        return articleLoadingStatus
    }

    fun getLatestArticles() : LiveData<List<Article>> {
        //only pull if the articles are null
        if(latestArticles.value.isNullOrEmpty() || searchTerm != Repository.liveSearchTerm.value) {
            latestArticles = Repository.getLatest(searchTerm)
        }
        return latestArticles
    }
}