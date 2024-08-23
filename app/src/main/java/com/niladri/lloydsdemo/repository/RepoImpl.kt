package com.niladri.lloydsdemo.repository

import com.niladri.lloydsdemo.api.RetroEndpointsService
import com.niladri.lloydsdemo.utils.Constants.ERROR_MSG
import com.niladri.lloydsdemo.utils.ResultWrapper
import javax.inject.Inject

/**
 * We will have all the Network calls in this class thus keeping the the repository class clean.
 * Hence the repo can be used only to store data across the app.
*/
class RepoImpl @Inject constructor(private val retroEndpointsService: RetroEndpointsService) : Repo() {

    override suspend fun getPosts() {
        postLiveData.postValue(ResultWrapper.loading())
        val response = retroEndpointsService.getPostData()
        if (response.isSuccessful && response.body() != null)
            postLiveData.postValue(ResultWrapper.success(response.body()))
        else if (response.errorBody() != null)
            postLiveData.postValue(ResultWrapper.error(response.errorBody().toString()))
        else
            postLiveData.postValue(ResultWrapper.error(ERROR_MSG))
    }

}