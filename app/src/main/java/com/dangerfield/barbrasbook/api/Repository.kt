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
    private val apiKey = "0cec05e663864f78867ef7af73988cc2"
    private val articleFeed = MutableLiveData<Resource<List<Article>>>()
    private val db = ArticlesDatabase(application)

    /***
     * fetches celebrity articles from
     * @Input: whether or not the request is for a refresh of data
     * @output new celebrity articles from api
     */
    fun getArticleFeed(refreshing: Boolean = false): MutableLiveData<Resource<List<Article>>> {

        articleFeed.value = Resource.Loading(refreshing=refreshing)

        if(Connectivity.isOnline) getFromApi() else getFromDatabase()

        return articleFeed
    }

    private fun getFromApi() {
        Log.d("API","Getting articles from API")

        articlesJob = Job()

        articlesJob?.let {runningJob ->
            //launched with job scope to make task cancelable from viewmodel
            CoroutineScope(IO + runningJob).launch {
                val call = RetrofitBuilder.apiService.getLatest(celebrity, apiKey)
                call.enqueue(object: retrofit2.Callback<Response> {

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Log.d("ERROR","Error when getting Latest Articles: "+t.localizedMessage)
                        getFromDatabase()
                    }

                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        Log.d("API","GOT articles from API")

                        articleFeed.postValue(Resource.Success(data = response.body()?.articles ?: listOf()))
                        writeToDataBase(response.body()?.articles)
                        runningJob.complete()
                    }
                })
            }
        }
    }

    private fun writeToDataBase(articles: List<Article>?) {
        Log.d("API","Writing to database with ${articles?.size ?: 0} articles")

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
                articleFeed.postValue(Resource.Error(data = db.articleDao().getAll(), message = "Could not load new articles"))
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