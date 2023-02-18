package com.example.collegeverse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.collegeverse.Model.Comment
import com.example.collegeverse.Utill.Constant
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.databinding.FragmentCommentBinding
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent

class Fragment_Comment:Fragment() {

    lateinit var binding: FragmentCommentBinding
    lateinit var mainViewModel: MainViewModel
    var commentRecyclerViewAdapter=CommentRecyclerViewAdapter()
    var database= Database()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentCommentBinding.inflate(inflater)
        mainViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getMainRepo()).get(
            MainViewModel::class.java)
        binding.commentRecyclerView.adapter=commentRecyclerViewAdapter
        binding.progressCircularLayout.visibility= View.VISIBLE
        mainViewModel.getallcomment(Constant.postid)
        mainViewModel.CommentList.observe(viewLifecycleOwner){
            binding.progressCircularLayout.visibility= View.INVISIBLE
            if(it.size==0)
                binding.text.visibility= View.VISIBLE
            else
                commentRecyclerViewAdapter.data(it)
        }
        binding.ChatSend.setOnClickListener(View.OnClickListener {
            val s = binding.ChatWrite.text.toString()
            if (s == "")
                return@OnClickListener
            binding.progressCircularLayout.visibility= View.VISIBLE
            mainViewModel.comment(Comment("",Constant.postid,database.GetToken(context!!)!!,"","",s))
            mainViewModel.Response.observe(viewLifecycleOwner){
                binding.progressCircularLayout.visibility= View.INVISIBLE
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT)
                if(it.success.equals("true"))
                    mainViewModel.getallcomment(Constant.postid)
                binding.ChatWrite.setText("")
                binding.ChatWrite.isSelected = true
            }
        })
        return binding.root
    }
}