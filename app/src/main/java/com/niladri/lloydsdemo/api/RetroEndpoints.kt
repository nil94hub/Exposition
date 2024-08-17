package com.niladri.lloydsdemo.api

import com.niladri.lloydsdemo.model.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface RetroEndpoints {

    @GET("posts")
    suspend fun getPostData(): Response<PostResponse>
}