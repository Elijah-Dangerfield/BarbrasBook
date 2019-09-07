package com.dangerfield.barbrasbook.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.networking.LoadingStatus
import com.dangerfield.barbrasbook.networking.Repository

class MainViewModel: ViewModel() {

    private var latestArticles = MutableLiveData<List<Article>>()
    private var articleLoadingStatus = MutableLiveData<LoadingStatus>()

    fun cancelJobs() { Repository.cancelJobs() }

    fun refreshArticles() {
        latestArticles = Repository.getLatest(refreshing = true)
    }

    fun getArticleLoadingStatus(): LiveData<LoadingStatus> {
        articleLoadingStatus = Repository.getArticleLoadingStatus()
        return articleLoadingStatus
    }

    fun getLatestArticles() : LiveData<List<Article>> {
        latestArticles = Repository.getLatest()
        return latestArticles
    }
}