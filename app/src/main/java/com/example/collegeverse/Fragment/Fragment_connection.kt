package com.example.collegeverse.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.collegeverse.Adapter.ConnectionRecyclerViewAdapter
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.databinding.FragmentConnectionBinding
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent

class Fragment_connection: Fragment() {

    lateinit var binding: FragmentConnectionBinding
    lateinit var mainViewModel: MainViewModel
    var connectionRecyclerViewAdapter= ConnectionRecyclerViewAdapter()
    var database= Database()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentConnectionBinding.inflate(inflater)
        mainViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getMainRepo()).get(
            MainViewModel::class.java)
        binding.ChatR.adapter=connectionRecyclerViewAdapter
        binding.progressCircularLayout.visibility= View.VISIBLE
        database.GetToken(binding.root.context)?.let { mainViewModel.getallConnection(it) }
        mainViewModel.UserList.observe(viewLifecycleOwner){
            binding.progressCircularLayout.visibility= View.INVISIBLE
            if(it.size==0)
                binding.text.visibility=View.VISIBLE
            else
                connectionRecyclerViewAdapter.data(it)
        }
        return binding.root
    }
}