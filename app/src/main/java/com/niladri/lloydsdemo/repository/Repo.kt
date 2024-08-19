package com.niladri.lloydsdemo.repository

import androidx.lifecycle.MutableLiveData
import com.niladri.lloydsdemo.model.PostResponse
import com.niladri.lloydsdemo.utils.ResultWrapper


abstract class Repo: Repository {
    //The value is updated anytime we receive a response from backend. In Future we can add more liveDatas here.
    val postLiveData = MutableLiveData<ResultWrapper<PostResponse>>()

    //Our abstract functions responsible for network calls
    abstract suspend fun getPosts()
}