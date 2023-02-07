package com.example.collegeverse.di

import com.example.collegeverse.Retrofit.AppApi
import com.example.collegeverse.Utill.Constant
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit
    {
        val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS).build()
        return Retrofit.Builder().baseUrl(Constant.BASE_URL).client(client).addConverterFactory(
            GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesAppApi(retrofit: Retrofit): AppApi =retrofit.create(AppApi::class.java)
}