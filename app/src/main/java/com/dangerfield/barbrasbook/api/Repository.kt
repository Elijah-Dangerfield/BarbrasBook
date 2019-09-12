package com.dangerfield.barbrasbook.api

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.dangerfield.barbrasbook.db.ArticlesDatabase
import com.dangerfield.barbrasbook.model.Article
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Call

class Repository(private val celebrity: String, application: Application) {

    private var articlesJob: CompletableJob? = null
    private val API_KEY = "0cec05e663864f78867ef7af73988cc2"
    private val articleLoadingStatus = MutableLiveData<LoadingStatus>()
    private val articles = MutableLiveData<List<Article>>()
    private val db = ArticlesDatabase(application)


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
        articleLoadingStatus.value =
            if(refreshing)LoadingStatus.REFRESHING else LoadingStatus.LOADING

        if(Connectivity.isOnline) getFromApi() else getFromDatabase()

        return articles
    }

    private fun getFromApi() {
        Log.d("API","Getting articles from API")

        articlesJob = Job()

        articlesJob?.let {runningJob ->
            //launched with job scope to make task cancelable from viewmodel
            CoroutineScope(IO + runningJob).launch {
                val call = RetrofitBuilder.apiService.getLatest(celebrity, API_KEY)
                call.enqueue(object: retrofit2.Callback<Response> {

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Log.d("ERROR","Error when getting Latest Articles: "+t.localizedMessage)
                        getFromDatabase()
                    }

                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        Log.d("API","GOT articles from API")

                        articles.value = response.body()?.articles
                        writeToDataBase(response.body()?.articles)
                        articles.postValue(response.body()?.articles)
                        runningJob.complete()
                        articleLoadingStatus.postValue(LoadingStatus.LOADED)
                    }
                })
            }
        }
    }

    private fun writeToDataBase(articles: List<Article>?) {
        Log.d("API","Writing to database with \n\n\n\n $articles")

        articlesJob = Job()

        articlesJob?.let {runningJob ->
            CoroutineScope(IO + runningJob).launch {
                db.articleDao().updateAll(articles ?: listOf())
            }
        }
    }

    private fun getFromDatabase() {
        Log.d("API","Getting articles from DB")
        articlesJob = Job()

        articlesJob?.let {runningJob ->
            CoroutineScope(IO + runningJob).launch {
                articles.postValue( db.articleDao().getAll())
                //loading set to failed to let view know that the network request did not go through
                articleLoadingStatus.postValue(LoadingStatus.FAILED)
                Log.d("API","GOT articles from DB")
                runningJob.complete()
            }
        }
    }

    /**
     * cancels ongoing jobs
     */
    fun cancelJobs() { articlesJob?.cancel() }
}