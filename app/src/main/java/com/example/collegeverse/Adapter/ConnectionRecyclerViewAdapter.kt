package com.example.collegeverse.Adapter

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
import com.example.collegeverse.Model.Connection
import com.example.collegeverse.Model.User
import com.example.collegeverse.R
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent


class ConnectionRecyclerViewAdapter() : RecyclerView.Adapter<ConnectionRecyclerViewAdapter.ViewHolder>() {

    lateinit var mainViewModel: MainViewModel
    var userList= emptyList<User>()
    var database= Database()
    lateinit var id:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.connection_layout, parent, false)
        mainViewModel = ViewModelProvider(
            (view.context as ViewModelStoreOwner),
            DaggerApplicationComponent.builder().build().getMainRepo()).get(
            MainViewModel::class.java)
        id= database.GetToken(view.context).toString()
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user=userList[position]
        Glide.with(holder.itemView).load(user.image).centerCrop().placeholder(R.drawable.profile).into(holder.pic)
        holder.name.text=user.name
        holder.delete.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(it.getContext())
            builder.setMessage("Do you want to remove from Connections ?").setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { dialog, which ->
                    mainViewModel.disconnect(Connection(id,user._id))
                    dialog.cancel()
                }.setNegativeButton(
                    "No"
                ) { dialog, which -> dialog.cancel() }.show()
        })
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun data(userList: List<User>)
    {
        this.userList=userList
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView
        var name: TextView
        var delete:ImageView

        init {
            pic = itemView.findViewById(R.id.UserImage)
            name = itemView.findViewById(R.id.UserName)
            delete=itemView.findViewById(R.id.delete)
        }
    }

}