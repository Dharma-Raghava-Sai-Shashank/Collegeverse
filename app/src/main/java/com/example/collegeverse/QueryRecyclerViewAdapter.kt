package com.example.collegeverse

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeverse.Model.Connection
import com.example.collegeverse.Model.Like
import com.example.collegeverse.Model.Post
import com.example.collegeverse.Utill.Constant
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent


class QueryRecyclerViewAdapter : RecyclerView.Adapter<QueryRecyclerViewAdapter.ViewHolder>(){

    lateinit var mainViewModel: MainViewModel
    var postList= emptyList<Post>()
    var database= Database()
    lateinit var id:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryRecyclerViewAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.query_layout, parent, false)
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
        Glide.with(holder.itemView).load(post.image).centerCrop().placeholder(R.drawable.logo).into(holder.PostImage)
        holder.Suject.text = post.postname
        holder.Description.text = post.postdetail

//         Connection :
        holder.Send.setOnClickListener(View.OnClickListener {
            if (holder.BlueSend.getVisibility() == View.INVISIBLE) {
                holder.BlueSend.setVisibility(View.VISIBLE)
                holder.Send.visibility= View.INVISIBLE
                mainViewModel.connect(Connection(id,post.createrid))
            } else {
                holder.BlueSend.setVisibility(View.INVISIBLE)
                holder.Send.visibility= View.VISIBLE
            }
        })

        // Comment :
        holder.comment.setOnClickListener(View.OnClickListener {
            Constant.postid=post._id
            holder.Go.callOnClick()
        })

        holder.PostImage.setOnClickListener(View.OnClickListener {
            // Dialog Box :
            val fullimage: AlertDialog.Builder = AlertDialog.Builder(it.context)
            val view: View = LayoutInflater.from(it.context).inflate(R.layout.fullimage_dialog, null)
            val FullHouseImage = view.findViewById<ImageView>(R.id.FullImage)
            fullimage.setView(view)

            Glide.with(holder.itemView).load(post.createrimage).centerCrop().placeholder(R.drawable.postimage).into(FullHouseImage)

            FullHouseImage.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            FullHouseImage.scaleType = ImageView.ScaleType.FIT_XY

            val alertDialog: AlertDialog = fullimage.create()
            alertDialog.setCanceledOnTouchOutside(true)
            alertDialog.show()
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

}
