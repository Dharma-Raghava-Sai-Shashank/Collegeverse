package com.example.collegeverse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.collegeverse.Utill.Constant
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.databinding.FragmentHomeBinding
import com.example.collegeverse.databinding.FragmentLikeBinding
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent

class Fragment_liked: Fragment() {

    lateinit var binding: FragmentLikeBinding
    lateinit var mainViewModel: MainViewModel
    var postRecyclerViewAdapter=PostRecyclerViewAdapter()
    var database=Database()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentLikeBinding.inflate(inflater)
        mainViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getMainRepo()).get(
            MainViewModel::class.java)
        binding.LikeRecyclerView.adapter=postRecyclerViewAdapter
        binding.progressCircularLayout.visibility= View.VISIBLE
        database.GetToken(binding.root.context)?.let { mainViewModel.getallLike(it) }
        mainViewModel.LikedPostList.observe(viewLifecycleOwner){
            binding.progressCircularLayout.visibility= View.INVISIBLE
            if(it.size==0)
                binding.text.visibility=View.VISIBLE
            else
                postRecyclerViewAdapter.data(it)
            Constant.LikedPost(it)
        }
        return binding.root
    }
}