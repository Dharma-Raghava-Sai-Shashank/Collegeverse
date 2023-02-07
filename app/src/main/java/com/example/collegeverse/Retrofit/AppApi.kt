package com.example.collegeverse.Retrofit

import com.example.collegeverse.Model.User
import retrofit2.Response
import retrofit2.http.GET

interface AppApi
{
    @GET("about")
    suspend fun getUsers(): Response<List<User>>
}