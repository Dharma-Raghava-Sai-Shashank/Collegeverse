package com.example.collegeverse.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.collegeverse.Model.SigninUser
import com.example.collegeverse.R
import com.example.collegeverse.ViewModel.RegistrationViewModel
import com.example.collegeverse.databinding.SigninUserBinding
import com.example.collegeverse.di.DaggerApplicationComponent

class Signin : Fragment(R.layout.signin_user) {

    lateinit var signinUserBinding: SigninUserBinding
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        signinUserBinding= SigninUserBinding.inflate(inflater)
        registrationViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getRegistrtionRepo()).get(RegistrationViewModel::class.java)


        signinUserBinding.textViewForgotPassword.setOnClickListener(View.OnClickListener {
            startActivity(Intent(context, ForgotPassword::class.java))
        })

        signinUserBinding.LogInButton.setOnClickListener(View.OnClickListener {

            // Intialising Variables:
            val Email: String = signinUserBinding.editTextEmail.getText().toString().trim()
            val Password: String = signinUserBinding.editTextPassword.getText().toString().trim()

            // Checking conditions:
            if (TextUtils.isEmpty(Email)) {
                signinUserBinding.editTextEmail.setError("Email is required")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(Password)) {
                signinUserBinding.editTextPassword.setError("Password is required")
                return@OnClickListener
            }
            if (Password.length < 6) {
                signinUserBinding.editTextPassword.setError("Password must be >= 6 characters")
                return@OnClickListener
            }

            // Signin Activity :
            signinUserBinding.progressCircularLayout.setVisibility(View.VISIBLE);
            Signin(Email,Password);
        })
        return signinUserBinding.root
    }

    // SignUp Activity :
    fun Signin(Email: String, Password: String) {
        registrationViewModel.Signin(SigninUser(Email,Password))
        registrationViewModel.LoginStatus.observe(viewLifecycleOwner){response->
            Log.d("123456",response.toString())
            Toast.makeText(context,response.message, Toast.LENGTH_SHORT).show()
            if(response.success.equals("true"))
            {
                registrationViewModel.SetToken(response.token, context!!)
                startActivity(Intent(context, MainActivity::class.java))
            }
            signinUserBinding.progressCircularLayout.setVisibility(View.INVISIBLE);
        }
    }
}