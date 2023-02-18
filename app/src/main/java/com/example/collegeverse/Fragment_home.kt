package com.example.collegeverse

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.bumptech.glide.Glide
import com.example.collegeverse.Model.Post
import com.example.collegeverse.Utill.Constant
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.databinding.FragmentHomeBinding
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent

class Fragment_home: Fragment() {

    lateinit var binding:FragmentHomeBinding
    lateinit var mainViewModel: MainViewModel
    var postRecyclerViewAdapter=PostRecyclerViewAdapter()
    var queryRecyclerViewAdapter=QueryRecyclerViewAdapter()
    var database= Database()
    lateinit var Data:List<Post>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding=FragmentHomeBinding.inflate(inflater)
        mainViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getMainRepo()).get(
            MainViewModel::class.java)

        binding.progressCircularLayout.visibility=View.VISIBLE
        mainViewModel.getallPost()
        mainViewModel.PostList.observe(viewLifecycleOwner){data->
            database.GetToken(binding.root.context)?.let { mainViewModel.getallLike(it) }
            mainViewModel.LikedPostList.observe(viewLifecycleOwner){
                Constant.LikedPost(it)
                binding.progressCircularLayout.visibility=View.INVISIBLE
                Data=data
                binding.post.setBackgroundResource(R.drawable.post_bac)
                binding.query.background=null
                binding.HomeRecyclerView.adapter=postRecyclerViewAdapter
                postRecyclerViewAdapter.data(Data.filter { it.is_post==1 })
            }
        }

        binding.post.setOnClickListener(View.OnClickListener {
            binding.post.setBackgroundResource(R.drawable.post_bac)
            binding.query.background=null
            binding.HomeRecyclerView.adapter=postRecyclerViewAdapter
            postRecyclerViewAdapter.data(Data.filter { it.is_post==1 })
        })

        binding.query.setOnClickListener(View.OnClickListener {
            binding.query.setBackgroundResource(R.drawable.post_bac)
            binding.post.background=null
            binding.HomeRecyclerView.adapter=queryRecyclerViewAdapter
            queryRecyclerViewAdapter.data(Data.filter { it.is_post==0 })
        })
        return binding.root
    }
}