package com.dangerfield.barbrasbook.ui.articleFeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.api.Repository
import com.dangerfield.barbrasbook.api.Resource

class NewsViewModel(private val repository: Repository): ViewModel() {

    private var latestArticles = MutableLiveData<Resource<List<Article>>>()

    /**
     * cancles all jobs in view model
     */
    fun cancelJobs() { repository.cancelJobs() }

    /**
     * Force pulls new articles from api in repository
     * sets live data's variable
     */
    fun refreshArticles() {
        latestArticles = repository.getArticles() as MutableLiveData<Resource<List<Article>>>
    }

    /**
     * Retrives new articles from repository if
     * articles data is empty, otherwise returns the stored value
     */
    fun getLatestArticles() : LiveData<Resource<List<Article>>> {
        if(latestArticles.value?.data.isNullOrEmpty()) {
            latestArticles = repository.getArticles() as MutableLiveData<Resource<List<Article>>>
        }
        return latestArticles
    }
}