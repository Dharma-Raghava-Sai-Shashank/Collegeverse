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
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.collegeverse.ViewModel.RegistrationViewModel
import com.example.collegeverse.databinding.FragmentGenerateOtpBinding
import com.example.collegeverse.databinding.FragmentPasswordResetBinding
import com.example.collegeverse.di.DaggerApplicationComponent

class ResetPassword:Fragment(R.layout.fragment_password_reset) {

    lateinit var binding: FragmentPasswordResetBinding
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentPasswordResetBinding.inflate(inflater)
        registrationViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getRegistrtionRepo()).get(RegistrationViewModel::class.java)

        binding.buttonSetPassword.setOnClickListener(View.OnClickListener {

            if (TextUtils.isEmpty(binding.editTextPassword.text)) {
                binding.editTextPassword.setError("Password is required")
                return@OnClickListener
            }
            if (binding.editTextPassword.text.length < 6) {
                binding.editTextPassword.setError("Password must be >= 6 characters")
                return@OnClickListener
            }
            if (!binding.editTextPassword.text.toString().equals( binding.editTextConfirmPassword.text.toString())) {
                binding.editTextConfirmPassword.setError("Recheck password")
                return@OnClickListener
            }

            binding.progressCircularLayout.visibility=View.VISIBLE
            registrationViewModel.resetPassword(binding.editTextPassword.text.toString())
            registrationViewModel.VerifyOTP.observe(viewLifecycleOwner){response->
                binding.progressCircularLayout.visibility=View.INVISIBLE
                Toast.makeText(context,response.message, Toast.LENGTH_SHORT).show()
                if(response.success.equals("true"))
                    startActivity(Intent(context,Registration::class.java))
            }
        })
        return binding.root
    }
}