package com.example.collegeverse.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.collegeverse.R
import com.example.collegeverse.ViewModel.RegistrationViewModel
import com.example.collegeverse.databinding.FragmentVerifyOtpBinding
import com.example.collegeverse.di.DaggerApplicationComponent

class VerifyOTP:Fragment(R.layout.fragment_verify_otp) {

    lateinit var binding: FragmentVerifyOtpBinding
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentVerifyOtpBinding.inflate(inflater)
        registrationViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getRegistrtionRepo()).get(RegistrationViewModel::class.java)

        binding.VerifyButton.setOnClickListener(View.OnClickListener {

            if (binding.editTextOtp.text.length < 6) {
                binding.editTextOtp.setError("OTP must be 6 digits")
                return@OnClickListener
            }

            binding.progressCircularLayout.visibility=View.VISIBLE
            registrationViewModel.verifyOTP(binding.editTextOtp.text.toString())
            registrationViewModel.VerifyOTP.observe(viewLifecycleOwner){response->
                binding.progressCircularLayout.visibility=View.INVISIBLE
                Toast.makeText(context,response.message, Toast.LENGTH_SHORT).show()
                if(response.success.equals("true"))
                    findNavController().navigate(R.id.resetPassword)
            }
        })
        return binding.root
    }
}