package com.example.collegeverse

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeverse.Model.Comment
import com.example.collegeverse.Model.Connection
import com.example.collegeverse.Model.User
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent

class CommentRecyclerViewAdapter() : RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>() {

    var commentList= emptyList<Comment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.comment_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment=commentList[position]
        Glide.with(holder.itemView).load(comment.commenterimage).centerCrop().placeholder(R.drawable.profile).into(holder.pic)
        holder.name.text=comment.commentername
        holder.comment.text=comment.comment
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun data(commentList: List<Comment>)
    {
        this.commentList=commentList
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView
        var name: TextView
        var comment: TextView

        init {
            pic = itemView.findViewById(R.id.UserImage)
            name = itemView.findViewById(R.id.UserName)
            comment=itemView.findViewById(R.id.Usercomment)
        }
    }

}