package com.dangerfield.barbrasbook.networking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dangerfield.barbrasbook.model.Article
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Call

class Repository(private val celebrity: String) {

    private var articlesJob: CompletableJob? = null
    val API_KEY = "0cec05e663864f78867ef7af73988cc2"
    private val articleLoadingStatus = MutableLiveData<LoadingStatus>()
    private val articles = MutableLiveData<List<Article>>()

    /**
     * returns the current loading status of the request to get articles
     * used to help view display loading icons
     */
    fun getArticleLoadingStatus(): MutableLiveData<LoadingStatus> = articleLoadingStatus

    /***
     * fetches celebrity articles from
     * @Input: whether or not the request is for a refresh of data
     * @output new celebrity articles from api
     */
    fun getLatest(refreshing: Boolean = false): MutableLiveData<List<Article>> {

        if(Connectivity.isOnline) getFromApi(refreshing) else getFromDatabase()

        return articles
    }

    private fun getFromApi(refreshing: Boolean) {
        articleLoadingStatus.value = if(refreshing)LoadingStatus.REFRESHING else LoadingStatus.LOADING
        articlesJob = Job()

        articlesJob?.let {runningJob ->
            //launched with job scope to make task cancelable from viewmodel
            CoroutineScope(IO + runningJob).launch {
                val call = RetrofitBuilder.apiService.getLatest(celebrity, API_KEY)
                call.enqueue(object: retrofit2.Callback<Response> {

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        articleLoadingStatus.postValue(LoadingStatus.FAILED)
                        Log.d("ERROR","Error when getting Latest Articles: "+t.localizedMessage)
                        //TODO: consider calling getFromDatabase and toasting it
                    }

                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        articles.value = response.body()?.articles
                        articles.postValue(response.body()?.articles)
                        runningJob.complete()
                        articleLoadingStatus.postValue(LoadingStatus.LOADED)
                        //TODO consider writing to database here
                    }
                })
            }
        }
    }

    private fun getFromDatabase() {

    }

    /**
     * cancels ongoing jobs
     */
    fun cancelJobs() { articlesJob?.cancel() }
}