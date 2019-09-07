package com.dangerfield.barbrasbook.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dangerfield.barbrasbook.model.RealArticle
import com.dangerfield.barbrasbook.networking.Repository

class MainViewModel: ViewModel() {

    fun getLatestArticles(): LiveData<List<RealArticle>> = Repository.getLatest()

    fun cancelJobs() {
        Repository.cancelJobs()
    }

}