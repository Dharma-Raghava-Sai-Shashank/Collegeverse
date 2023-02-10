package com.example.collegeverse

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.collegeverse.Model.GenerateOTP
import com.example.collegeverse.Utill.Constant
import com.example.collegeverse.ViewModel.RegistrationViewModel
import com.example.collegeverse.databinding.FragmentGenerateOtpBinding
import com.example.collegeverse.di.DaggerApplicationComponent

class GenerateOTP:Fragment(R.layout.fragment_generate_otp){

    lateinit var binding: FragmentGenerateOtpBinding
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding=FragmentGenerateOtpBinding.inflate(inflater)
        registrationViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getRegistrtionRepo()).get(RegistrationViewModel::class.java)

        binding.buttonGenerateOtp.setOnClickListener(View.OnClickListener {

            if (TextUtils.isEmpty(binding.editTextEmail.text)) {
                binding.editTextEmail.setError("Email is required")
                return@OnClickListener
            }

            binding.progressCircularLayout.visibility=View.VISIBLE
            Constant.email=binding.editTextEmail.text.toString().trim()
            registrationViewModel.generateOTP(GenerateOTP(binding.editTextEmail.text.toString().trim()))
            registrationViewModel.VerifyOTP.observe(viewLifecycleOwner){response->
                binding.progressCircularLayout.visibility=View.INVISIBLE
                Toast.makeText(context,response.message, Toast.LENGTH_SHORT).show()
                if(response.success.equals("true"))
                    findNavController().navigate(R.id.verifyOTP)
            }
        })


        return binding.root
    }
}