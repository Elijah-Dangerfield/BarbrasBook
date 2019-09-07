package com.dangerfield.barbrasbook.networking

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dangerfield.barbrasbook.model.RealArticle
import kotlinx.coroutines.*
import retrofit2.Call

object Repository {

    var job: CompletableJob? = null
    val celebrity = "barbra streisand"
    private val API_KEY = "0cec05e663864f78867ef7af73988cc2"
    private val articles = MutableLiveData<List<RealArticle>>()

    fun getLatest(): LiveData<List<RealArticle>> {
        job = Job()

        val call = RetrofitBuilder.apiService.getLatest(celebrity, API_KEY)
        call.enqueue(object: retrofit2.Callback<Response>
        {
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.d("HI","HI")

            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                Log.d("HI","HI")
                articles.value = response.body()?.articles
            }

        })

      return articles
    }

    fun cancelJobs() {
        job?.cancel()
    }
}