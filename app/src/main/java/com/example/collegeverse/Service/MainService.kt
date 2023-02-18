package com.example.collegeverse.Service

import com.example.collegeverse.Model.*
import retrofit2.Response
import retrofit2.http.*

interface MainService
{
    @POST("post/post")
    suspend fun post(@Body post: Post): Response<Responser>

    @GET("post/getall")
    suspend fun getallPost(): Response<List<Post>>

    @GET("post/getall/{id}")
    suspend fun getallPostById(@Path("id")id:String): Response<List<Post>>

    @GET("post/get/{id}")
    suspend fun getPostById(@Path("id") id:String): Response<Post>

    @POST("post/update")
    suspend fun updatePost(@Body post:Post): Response<Responser>

    @GET("user/get/{id}")
    suspend fun getUserById(@Path("id")id:String): Response<User>

    @POST("user/update")
    suspend fun updateUser(@Body user: User): Response<Responser>

    @POST("like/like")
    suspend fun like(@Body like: Like):Response<Responser>

    @POST("like/unlike")
    suspend fun unlike(@Body like: Like):Response<Responser>

    @GET("like/getall/{id}")
    suspend fun getallLike(@Path("id")id:String): Response<List<Post>>

    @POST("connection/connect")
    suspend fun connect(@Body connection: Connection):Response<Responser>

    @POST("connection/disconnect")
    suspend fun disconnect(@Body connection: Connection):Response<Responser>

    @GET("connection/getall/{id}")
    suspend fun getallConnection(@Path("id")id:String): Response<List<User>>

    @POST("comment/comment")
    suspend fun comment(@Body comment: Comment):Response<Responser>

    @GET("comment/getall/{id}")
    suspend fun getallcomment(@Path("id")id:String): Response<List<Comment>>

}