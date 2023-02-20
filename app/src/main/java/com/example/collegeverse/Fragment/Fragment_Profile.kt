package com.example.collegeverse.Fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.collegeverse.Adapter.PostRecyclerViewAdapter
import com.example.collegeverse.Model.User
import com.example.collegeverse.R
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.databinding.FragmentProfileBinding
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent
import com.google.firebase.storage.FirebaseStorage

class Fragment_Profile: Fragment(){

    lateinit var binding: FragmentProfileBinding
    lateinit var mainViewModel: MainViewModel
    var postRecyclerViewAdapter= PostRecyclerViewAdapter()
    var database= Database()
    var firebaseStorage=FirebaseStorage.getInstance()
    var storage=firebaseStorage.getReference()
    var aa = 0
    lateinit var uri: Uri
    lateinit var bitmap: Bitmap
    var N = 0
    var E = 0
    var P = 0
    var A = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding= FragmentProfileBinding.inflate(inflater)
        mainViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getMainRepo()).get(
            MainViewModel::class.java)

        var sName:String=""
        var sEmail:String=""
        var sPhone:String=""
        var sAbout:String=""

        binding.postRecyclerview.adapter=postRecyclerViewAdapter
        binding.progressCircularLayout.visibility = View.VISIBLE
        context?.let { database.GetToken(it)?.let { mainViewModel.getallPostById(it) } }
        mainViewModel.PostList.observe(viewLifecycleOwner){data->
            context?.let { database.GetToken(it)?.let { mainViewModel.getUserById(it) } }
            mainViewModel.User.observe(viewLifecycleOwner){
                if(data.size==0)
                    binding.text.visibility=View.VISIBLE
                else
                    postRecyclerViewAdapter.data(data)
                Glide.with(this).load(it.image).centerCrop().placeholder(R.drawable.profile).into(binding.ProfilePersonImage)
                binding.ProfileName.setText("Name: " + it.name)
                binding.ProfileEmail.setText("Email: " + it.email)
                binding.ProfilePhone.setText("Phone: " + it.phone)
                binding.ProfileAbout.setText("About: " + it.about)
                binding.progressCircularLayout.visibility = View.INVISIBLE
                sName = binding.ProfileName.text.toString().substring(6)
                sEmail = binding.ProfileEmail.text.toString().substring(7)
                sPhone = binding.ProfilePhone.text.toString().substring(7)
                sAbout = binding.ProfileAbout.text.toString().substring(7)
            }
        }
        binding.EditEmail.visibility=View.INVISIBLE


        // Image Selection:
        binding.ProfileCamera.setOnClickListener {
            aa = 1
            binding.submit.visibility = View.VISIBLE
            imageChooser()
        }

        // Editing Profile :
        binding.EditName.setOnClickListener {
            N = 1
            binding.ProfileName.visibility = View.INVISIBLE
            binding.EditProfileName.visibility = View.VISIBLE
            binding.submit.visibility = View.VISIBLE
        }
        binding.EditEmail.setOnClickListener {
            E = 1
            binding.ProfileEmail.visibility = View.INVISIBLE
            binding.EditProfileEmail.visibility = View.VISIBLE
            binding.submit.visibility = View.VISIBLE
        }
        binding.EditPhone.setOnClickListener {
            P = 1
            binding.ProfilePhone.visibility = View.INVISIBLE
            binding.EditProfilePhone.visibility = View.VISIBLE
            binding.submit.visibility = View.VISIBLE
        }
        binding.EditAbout.setOnClickListener {
            A = 1
            binding.ProfileAbout.visibility = View.INVISIBLE
            binding.EditProfileAbout.visibility = View.VISIBLE
            binding.submit.visibility = View.VISIBLE
        }

        binding.submit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                if (aa != 1) {
                    Toast.makeText(context, "Add Profile Image", Toast.LENGTH_SHORT).show()
                    return
                }

                binding.progressCircularLayout.visibility = View.VISIBLE

                if(N==1){
                    N=0;
                    binding.EditProfileName.setVisibility(View.INVISIBLE);
                    sName=binding.EditProfileName.getText().toString();
                    binding.ProfileName.setVisibility(View.VISIBLE);
                }
                if(E==1) {
                    E=0;
                    binding.EditProfileEmail.setVisibility(View.INVISIBLE);
                    sEmail = binding.EditProfileEmail.getText().toString();
                    binding.ProfileEmail.setVisibility(View.VISIBLE);
                }
                if(P==1) {
                    P=0;
                    binding.EditProfilePhone.setVisibility(View.INVISIBLE);
                    sPhone = binding.EditProfilePhone.getText().toString();
                    binding.ProfilePhone.setVisibility(View.VISIBLE);
                }
                if(A==1) {
                    A=0;
                    binding.EditProfileAbout.setVisibility(View.INVISIBLE);
                    sAbout = binding.EditProfileAbout.getText().toString();
                    binding.ProfileAbout.setVisibility(View.VISIBLE);
                }

                binding.submit.visibility = View.INVISIBLE

                // Uploading Photo to Firebase Storage :
                if (aa == 1) {
                    storage.child("Profile Image").putFile(uri!!).addOnSuccessListener {iit->
                        storage.child("Profile Image").downloadUrl.addOnSuccessListener { u->
                            mainViewModel.updateUser(User(context?.let { database.GetToken(it) }!!,sName,sEmail,sPhone,sAbout,u.toString()))
                        }
                    }
                    mainViewModel.Response.observe(viewLifecycleOwner){
                        binding.progressCircularLayout.visibility=View.INVISIBLE
                        Toast.makeText(context,it.message, Toast.LENGTH_SHORT).show()
                        binding.ProfileName.setText("Name: " + sName)
                        binding.ProfileEmail.setText("Email: " + sEmail)
                        binding.ProfilePhone.setText("Phone: " + sPhone)
                        binding.ProfileAbout.setText("About: " + sAbout)
                    }
                }
            }
        })

        return binding.root
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
                uri = data?.data!!
            try {
                val inputStream = requireActivity().contentResolver.openInputStream(uri!!)
                bitmap = BitmapFactory.decodeStream(inputStream)
                binding.ProfilePersonImage.setImageBitmap(bitmap)
                Log.d("123456",bitmap.toString())
            } catch (e: Exception) {
            }
        }
    }
}