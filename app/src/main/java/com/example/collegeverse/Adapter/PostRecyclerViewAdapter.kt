package com.example.collegeverse.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeverse.Model.Connection
import com.example.collegeverse.Model.Like
import com.example.collegeverse.Model.Post
import com.example.collegeverse.R
import com.example.collegeverse.Utill.Constant
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent


class PostRecyclerViewAdapter : RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder>(){

    lateinit var mainViewModel: MainViewModel
    var postList= emptyList<Post>()
    var database=Database()
    lateinit var id:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.post_layout, parent, false)
        mainViewModel = ViewModelProvider(
            (view.context as ViewModelStoreOwner),
        DaggerApplicationComponent.builder().build().getMainRepo()).get(
            MainViewModel::class.java)
        id= database.GetToken(view.context).toString()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val post=postList[position]

        Glide.with(holder.itemView).load(post.createrimage).centerCrop().placeholder(R.drawable.profile).into(holder.ProfilePic)
        holder.ProfileName.text = post.creatername
        Glide.with(holder.itemView).load(post.image).centerCrop().placeholder(R.drawable.postimage).into(holder.PostImage)
        holder.Suject.text = post.postname
        holder.Description.text = post.postdetail

        if(Constant.searchlike(post))
        {
            holder.Like.visibility=View.INVISIBLE
            holder.RedLike.visibility=View.VISIBLE
        }
        else
        {
            holder.Like.visibility=View.VISIBLE
            holder.RedLike.visibility=View.INVISIBLE
        }

        // Like :
        holder.itemView.setOnClickListener(object : DoubleClickListener() {
            override fun onSingleClick(v: View?) {}
            override fun onDoubleClick(v: View?) {
                if (holder.RedLike.getVisibility() == View.INVISIBLE) {
                    holder.RedLike.setVisibility(View.VISIBLE)
                    holder.Like.visibility=View.INVISIBLE
                    mainViewModel.like(Like("",post._id,id))
                } else {
                    holder.RedLike.setVisibility(View.INVISIBLE)
                    holder.Like.visibility=View.VISIBLE
                    mainViewModel.unlike(Like("",post._id,id))
                }
            }
        })
        holder.Like.setOnClickListener(View.OnClickListener {
            if (holder.RedLike.getVisibility() == View.INVISIBLE) {
                holder.RedLike.setVisibility(View.VISIBLE)
                holder.Like.visibility=View.INVISIBLE
                mainViewModel.like(Like("",post._id,id))
            } else {
                holder.RedLike.setVisibility(View.INVISIBLE)
                holder.Like.visibility=View.VISIBLE
                mainViewModel.unlike(Like("",post._id,id))
            }
        })
        holder.RedLike.setOnClickListener(View.OnClickListener {
            if (holder.RedLike.getVisibility() == View.INVISIBLE) {
                holder.RedLike.setVisibility(View.VISIBLE)
                holder.Like.visibility=View.INVISIBLE
                mainViewModel.like(Like("",post._id,id))
            } else {
                holder.RedLike.setVisibility(View.INVISIBLE)
                holder.Like.visibility=View.VISIBLE
                mainViewModel.unlike(Like("",post._id,id))
            }
        })

//         Connection :
        holder.Send.setOnClickListener(View.OnClickListener {
            if (holder.BlueSend.getVisibility() == View.INVISIBLE) {
                holder.BlueSend.setVisibility(View.VISIBLE)
                holder.Send.visibility=View.INVISIBLE
                mainViewModel.connect(Connection(id,post.createrid))
            } else {
                holder.BlueSend.setVisibility(View.INVISIBLE)
                holder.Send.visibility=View.VISIBLE
            }
        })

        // Comment :
        holder.comment.setOnClickListener(View.OnClickListener {
            Constant.postid=post._id
            holder.Go.callOnClick()
        })
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun data(postList: List<Post>)
    {
        this.postList=postList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var PostImage: ImageView
        var Like: ImageView
        var RedLike: ImageView
        var Send: ImageView
        var BlueSend: ImageView
        var ProfileName: TextView
        var Suject: TextView
        var Description: TextView
        var ProfilePic: ImageView
        var comment:TextView
        var Go:TextView

        init {
            RedLike = itemView.findViewById(R.id.RedLike)
            Like = itemView.findViewById(R.id.Like)
            Send = itemView.findViewById(R.id.Send)
            BlueSend = itemView.findViewById(R.id.BlueSend)
            PostImage = itemView.findViewById(R.id.LPostImage)
            Suject = itemView.findViewById(R.id.RSubject)
            Description = itemView.findViewById(R.id.LDescription)
            ProfilePic = itemView.findViewById(R.id.LProfilePic)
            ProfileName = itemView.findViewById(R.id.LProfileName)
            comment=itemView.findViewById(R.id.comment)
            Go=itemView.findViewById(R.id.comment2)
        }
    }

    // On Double Tap :
    abstract class DoubleClickListener : View.OnClickListener {
        var lastClickTime: Long = 0
        override fun onClick(v: View) {
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                onDoubleClick(v)
                lastClickTime = 0
            } else {
                onSingleClick(v)
            }
            lastClickTime = clickTime
        }

        abstract fun onSingleClick(v: View?)
        abstract fun onDoubleClick(v: View?)

        companion object {
            private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 //milliseconds
        }
    }

}
