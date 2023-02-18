package com.example.collegeverse

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.collegeverse.Model.Post
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.ViewModel.RegistrationViewModel
import com.example.collegeverse.databinding.FragmentPostBinding
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent

class Fragment_Post: Fragment() {

    lateinit var binding:FragmentPostBinding
    lateinit var mainViewModel: MainViewModel
    var database=Database()
    var uri: Uri? = null
    var bitmap: Bitmap? = null
    var a = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding=FragmentPostBinding.inflate(inflater)
        mainViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getMainRepo()).get(
            MainViewModel::class.java)

        // Post :
        binding.Camera?.setOnClickListener(View.OnClickListener {
            imageChooser()
            a = 1
        })

        binding.Post?.setOnClickListener(View.OnClickListener {
            if (a != 1) {
                Toast.makeText(context, "Please Add Image", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }

            // Alert Box :
            var builder : AlertDialog.Builder = AlertDialog.Builder(getContext())
            builder.setMessage("Share as ?").setCancelable(true)
                .setPositiveButton("Post",
                    DialogInterface.OnClickListener { dialog, which ->
                        post(1)
                        dialog.cancel()
                    }).setNegativeButton("Query",
                    DialogInterface.OnClickListener { dialog, which ->
                        post(0)
                        dialog.cancel()
                    }).show()
        })
        return binding.root
    }

    fun post(a:Int)
    {
        binding.progressCircularLayout?.setVisibility(View.VISIBLE)
        mainViewModel.post(Post("",database.GetToken(context!!).toString(),"","",binding.Subject.text.toString(),binding.Description.text.toString(),bitmap.toString(),a))
        mainViewModel.Response.observe(viewLifecycleOwner){response->
            if(response.success.equals("true"))
            {
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
            }
            binding.progressCircularLayout?.setVisibility(View.INVISIBLE)
            binding.PostImage?.setImageResource(R.drawable.postimage)
            binding.Subject?.text=null
            binding.Description?.text=null
        }
    }

    // Image Selector:
    fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 200) {
            uri = data?.data
            try {
                val inputStream = requireActivity().contentResolver.openInputStream(
                    uri!!
                )
                bitmap = BitmapFactory.decodeStream(inputStream)
                binding.PostImage?.setImageBitmap(bitmap)
            } catch (e: Exception) {
            }
        }
    }
}