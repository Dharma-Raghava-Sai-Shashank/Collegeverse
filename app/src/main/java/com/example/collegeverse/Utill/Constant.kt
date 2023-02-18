package com.example.collegeverse.Utill

import com.example.collegeverse.Model.Post

object Constant {
    const val BASE_URL="https://collegeverse.onrender.com/"

    var email=""

    var postid=""

    lateinit var LikePost:List<Post>

    fun LikedPost(likedPost:List<Post>)
    {
        LikePost=likedPost
    }

    fun searchlike(post:Post):Boolean
    {
        for(p in LikePost)
        {
            if(p._id.equals(post._id))
                return true
        }
        return false
    }

}