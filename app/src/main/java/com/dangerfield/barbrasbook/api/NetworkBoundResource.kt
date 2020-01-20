package com.dangerfield.barbrasbook.api

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType> {

    private val result = MediatorLiveData<Resource<ResultType>>()

    /*
    immediately shows data from database, if it should fetch then it gets new data else
    it keeps the stream to the database
     */
    fun build(): NetworkBoundResource<ResultType> {
        result.value = Resource.Loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.Success(newData))
                }
            }
        }

        return this
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) { newData ->
            Log.d("Elijah","set loading in network bound resource")

            setValue(Resource.Loading(newData))
            //While fetching, show old data from db
        }

        result.addSource(apiResponse) { response ->
            //remove the source on the first response
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when(response) {
                //When it is successful, immediately store results.
                // Then tell result to load from db as success
                is ApiResponse.Success -> {
                    Log.d("Elijah","Got success in network bound resource")

                    saveCallResult(response.data!!)

                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.Success(newData))
                    }
                }

                is ApiResponse.Empty -> {
                    //when it is empty, thats fine for our purposes, so pull from db as success
                    Log.d("Elijah","Got empty in network bound resource")

                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.Success(newData))
                    }
                }
                //when it is an error, return as error
                is ApiResponse.Error -> {
                    Log.d("Elijah","Got error in network bound resource")

                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.Error(newData , response.message ?: "unknown error"))
                    }
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected abstract fun saveCallResult(item: ResultType)

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<ResultType>>
}