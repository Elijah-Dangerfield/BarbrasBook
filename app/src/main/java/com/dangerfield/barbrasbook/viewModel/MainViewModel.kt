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

    /**
     * cancles all jobs in view model
     */
    fun cancelJobs() { Repository.cancelJobs() }

    /**
     * Force pulls new articles from api in repository
     * sets live data's variable
     */
    fun refreshArticles() {
        latestArticles = Repository.getLatest(refreshing = true)
    }

    /**
     * Retrives loading status of articles from repository
     * {LOADING,REFRESHING,LOADED,FAILED}
     */
    fun getArticleLoadingStatus(): LiveData<LoadingStatus> {
        articleLoadingStatus = Repository.getArticleLoadingStatus()
        return articleLoadingStatus
    }

    /**
     * Retrives new articles from repository if
     * articles data is empty, otherwise returns the stored value
     */
    fun getLatestArticles() : LiveData<List<Article>> {
        if(latestArticles.value.isNullOrEmpty()) {
            latestArticles = Repository.getLatest()
        }
        return latestArticles
    }
}