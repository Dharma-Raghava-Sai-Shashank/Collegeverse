package com.example.collegeverse.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeverse.Model.*
import com.example.collegeverse.Repository.MainRepo
import kotlinx.coroutines.launch

class MainViewModel(var mainRepo: MainRepo):ViewModel() {

    // MutableLiveData :
    var _Response= MutableLiveData<Responser>()
    var _Post= MutableLiveData<Post>()
    var _PostList= MutableLiveData<List<Post>>()
    var _LikedPostList= MutableLiveData<List<Post>>()
    var _User= MutableLiveData<User>()
    var _UserList= MutableLiveData<List<User>>()
    var _CommentList= MutableLiveData<List<Comment>>()


    // LiveData :
    var Response: LiveData<Responser> = _Response
    var Post: LiveData<Post> = _Post
    var PostList: LiveData<List<Post>> = _PostList
    var LikedPostList: LiveData<List<Post>> = _LikedPostList
    var User:LiveData<User> = _User
    var UserList: LiveData<List<User>> = _UserList
    var CommentList: LiveData<List<Comment>> = _CommentList


    fun post(post:Post)
    {
        viewModelScope.launch {
            _Response.postValue(mainRepo.post(post))
        }
    }

    fun getallPost()
    {
        viewModelScope.launch {
            _PostList.postValue(mainRepo.getallPost())
        }
    }

    fun getallPostById(id:String)
    {
        viewModelScope.launch {
            _PostList.postValue(mainRepo.getallPostById(id))
        }
    }

    fun getPostById(id:String)
    {
        viewModelScope.launch {
            _Post.postValue(mainRepo.getPostById(id))
        }
    }

    fun updatePost(post:Post)
    {
        viewModelScope.launch {
            _Response.postValue(mainRepo.updatePost(post))
        }
    }

    fun getUserById(id:String)
    {
        viewModelScope.launch {
            _User.postValue(mainRepo.getUserById(id))
        }
    }

    fun updateUser(user: User)
    {
        viewModelScope.launch {
            _Response.postValue(mainRepo.updateUser(user))
        }
    }

    fun like(like: Like)
    {
        viewModelScope.launch {
            _Response.postValue(mainRepo.like(like))
        }
    }

    fun unlike(like: Like)
    {
        viewModelScope.launch {
            _Response.postValue(mainRepo.unlike(like))
        }
    }

    fun getallLike(id:String)
    {
        viewModelScope.launch {
            _LikedPostList.postValue(mainRepo.getallLike(id))
        }
    }

    fun connect(connection: Connection)
    {
        viewModelScope.launch {
            _Response.postValue(mainRepo.connect(connection))
        }
    }

    fun disconnect(connection: Connection)
    {
        viewModelScope.launch {
            _Response.postValue(mainRepo.disconnect(connection))
        }
    }

    fun getallConnection(id:String)
    {
        viewModelScope.launch {
            _UserList.postValue(mainRepo.getallConnection(id))
        }
    }

    fun comment(comment: Comment)
    {
        viewModelScope.launch {
            _Response.postValue(mainRepo.comment(comment))
        }
    }

    fun getallcomment(id:String)
    {
        viewModelScope.launch {
            _CommentList.postValue(mainRepo.getallcomment(id))
        }
    }
}