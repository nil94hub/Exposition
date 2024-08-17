package com.niladri.lloydsdemo.repository

import androidx.lifecycle.MutableLiveData
import com.niladri.lloydsdemo.interfaces.Repository
import com.niladri.lloydsdemo.model.PostResponse
import com.niladri.lloydsdemo.utils.ResultWrapper


abstract class Repo: Repository {
    //The value is updated anytime we receive a response from backend
    val postLiveData = MutableLiveData<ResultWrapper<PostResponse>>()

    abstract suspend fun getPosts()
}