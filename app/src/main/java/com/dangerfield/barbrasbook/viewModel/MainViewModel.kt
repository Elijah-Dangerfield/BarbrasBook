package com.dangerfield.barbrasbook.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.barbrasbook.model.Article
import com.dangerfield.barbrasbook.networking.Repository

class MainViewModel: ViewModel() {

    fun getLatestArticles(): LiveData<List<Article>> = Repository.getLatest()

    fun cancelJobs() {
        Repository.cancelJobs()
    }

}