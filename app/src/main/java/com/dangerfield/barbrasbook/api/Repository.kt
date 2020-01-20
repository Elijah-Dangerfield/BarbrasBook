package com.dangerfield.barbrasbook.api

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dangerfield.barbrasbook.db.ArticlesDatabase
import com.dangerfield.barbrasbook.model.Article
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Call

class Repository(private val celebrity: String, application: Application) {

    private var articlesJob: CompletableJob? = null
    private val apiKey = "0cec05e663864f78867ef7af73988cc2"
    private val db = ArticlesDatabase(application)

    private val articleFeedResource = object : NetworkBoundResource<List<Article>>() {
        override fun saveCallResult(item: List<Article>) {
            writeToDataBase(item)
        }

        override fun shouldFetch(data: List<Article>?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<List<Article>> {
            return getFromDatabase()
        }

        override fun createCall(): LiveData<ApiResponse<List<Article>>> {
            return getFromApi()
        }
    }


    fun getArticles() : LiveData<Resource<List<Article>>> {
        return articleFeedResource.build().asLiveData()
    }


    private fun getFromApi(): LiveData<ApiResponse<List<Article>>> {
        Log.d("Elijah","Getting articles from API")

        articlesJob = Job()
        val result = MutableLiveData<ApiResponse<List<Article>>>()

        if(!Connectivity.isOnline) {
            result.postValue(ApiResponse.Error(null, "Error retrieving articles, please check network connection and try again"))
            return result
        }

        articlesJob?.let {runningJob ->
            //launched with job scope to make task cancelable from viewmodel
            CoroutineScope(IO + runningJob).launch {
                val call = RetrofitBuilder.apiService.getLatest(celebrity, apiKey)
                call.enqueue(object: retrofit2.Callback<Response> {

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Log.d("Elijah","Error when getting Latest Articles: "+t.localizedMessage)
                        result.postValue(ApiResponse.Error(null, t.localizedMessage ?: "Unknown Error"))
                    }

                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        Log.d("Elijah","GOT articles from API")
                        if(response.body()?.totalResults == 0){
                            result.postValue( ApiResponse.Empty())
                        }else{
                            result.postValue( ApiResponse.Success(response.body()?.articles ?: listOf()))
                        }
                        runningJob.complete()
                    }
                })
            }
        }
        return result
    }

    private fun writeToDataBase(articles: List<Article>?) {
        Log.d("Elijah","Writing to database with ${articles?.size ?: 0} articles")

        articlesJob = Job()

        articlesJob?.let {runningJob ->
            CoroutineScope(IO + runningJob).launch {
                db.articleDao().updateAll(articles ?: listOf())
            }
        }
    }

    private fun getFromDatabase(): LiveData<List<Article>> {
        return db.articleDao().getAll()
    }

    /**
     * cancels ongoing jobs
     */
    fun cancelJobs() { articlesJob?.cancel() }
}