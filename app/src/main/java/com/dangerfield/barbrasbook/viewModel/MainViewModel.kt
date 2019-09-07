package com.dangerfield.barbrasbook.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.networking.LoadingStatus
import com.dangerfield.barbrasbook.networking.Repository

class MainViewModel: ViewModel() {

    private var articleLoadingStatus = MutableLiveData<LoadingStatus>()

    fun getLatestArticles() = Repository.getLatest()

    fun getArticleLoadingStatus() = Repository.getArticleLoadingStatus()


    fun cancelJobs() {
        Repository.cancelJobs()
    }
}