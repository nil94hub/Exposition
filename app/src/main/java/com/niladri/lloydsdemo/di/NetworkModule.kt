package com.niladri.lloydsdemo.di

import com.niladri.lloydsdemo.api.RetroEndpointsService
import com.niladri.lloydsdemo.repository.Repo
import com.niladri.lloydsdemo.repository.RepoImpl
import com.niladri.lloydsdemo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun getInstance(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetroEndpoints(retrofit: Retrofit): RetroEndpointsService {
        return retrofit.create(RetroEndpointsService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepo(retroEndpoints: RetroEndpointsService): Repo {
        return RepoImpl(retroEndpoints)
    }
}
