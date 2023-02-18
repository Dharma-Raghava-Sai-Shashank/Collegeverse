package com.example.collegeverse.Repository

import com.example.collegeverse.Model.*
import com.example.collegeverse.Service.MainService
import com.example.collegeverse.db.Database
import javax.inject.Inject

class MainRepo @Inject constructor(var mainService: MainService,var database: Database){

    suspend fun post(post:Post): Responser?
    {
        return mainService.post(post).body()
    }

    suspend fun getallPost(): List<Post>?
    {
        return mainService.getallPost().body()
    }

    suspend fun getallPostById(id:String): List<Post>?
    {
        return mainService.getallPostById(id).body()
    }

    suspend fun getPostById(id:String):Post?
    {
        return mainService.getPostById(id).body()
    }

    suspend fun updatePost(post:Post): Responser?
    {
        return mainService.updatePost(post).body()
    }

    suspend fun getUserById(id:String): User?
    {
        return mainService.getUserById(id).body()
    }

    suspend fun updateUser(user: User): Responser?
    {
        return mainService.updateUser(user).body()
    }

    suspend fun like(like:Like): Responser?
    {
        return mainService.like(like).body()
    }

    suspend fun unlike(like:Like): Responser?
    {
        return mainService.unlike(like).body()
    }

    suspend fun getallLike(id:String): List<Post>?
    {
        return mainService.getallLike(id).body()
    }

    suspend fun connect(connection: Connection): Responser?
    {
        return mainService.connect(connection).body()
    }

    suspend fun disconnect(connection: Connection): Responser?
    {
        return mainService.disconnect(connection).body()
    }

    suspend fun getallConnection(id:String): List<User>?
    {
        return mainService.getallConnection(id).body()
    }

    suspend fun comment(comment: Comment): Responser?
    {
        return mainService.comment(comment).body()
    }

    suspend fun getallcomment(id:String): List<Comment>?
    {
        return mainService.getallcomment(id).body()
    }
}