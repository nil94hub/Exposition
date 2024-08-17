package com.niladri.lloydsdemo.repository

import android.util.Log
import com.niladri.lloydsdemo.api.RetroEndpoints
import com.niladri.lloydsdemo.utils.ResultWrapper
import javax.inject.Inject

class RepoImpl @Inject constructor(private val retroEndpoints: RetroEndpoints): Repo() {

    override suspend fun getPosts() {
        postLiveData.postValue(ResultWrapper.Loading())
        val response = retroEndpoints.getPostData()
        Log.d("Nil", response.code().toString())
        if (response.isSuccessful && response.body() != null)
            postLiveData.postValue(ResultWrapper.Success(response.body()!!))

        else if (response.errorBody() != null)
            postLiveData.postValue(ResultWrapper.Error(response.errorBody().toString()))

        else
            postLiveData.postValue(ResultWrapper.Error("Something Went wrong"))
    }

}