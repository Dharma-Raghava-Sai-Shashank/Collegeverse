package com.example.collegeverse.Service

import com.example.collegeverse.Model.User
import retrofit2.Response
import retrofit2.http.GET

interface AppService
{
    @GET("about")
    suspend fun getUsers(): Response<List<User>>
}