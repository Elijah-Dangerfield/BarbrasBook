package com.dangerfield.barbrasbook.networking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dangerfield.barbrasbook.model.Article
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import retrofit2.Call

object Repository {

    var job: CompletableJob? = null
    const val celebrity = "barbra streisand"
    private const val API_KEY = "0cec05e663864f78867ef7af73988cc2"
    private val articles = MutableLiveData<List<Article>>()

    fun getLatest(): LiveData<List<Article>> {
        job = Job()

        job?.let {runningJob ->
            CoroutineScope(IO + runningJob).launch {
                val call = RetrofitBuilder.apiService.getLatest(celebrity, API_KEY)
                call.enqueue(object: retrofit2.Callback<Response> {

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Log.d("ERROR","Error when getting Latest Articles: "+t.localizedMessage)
                    }

                    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                        articles.value = response.body()?.articles
                        runningJob.complete()
                    }
                })
            }
        }
        return articles
    }

    fun cancelJobs() {
        job?.cancel()
    }
}