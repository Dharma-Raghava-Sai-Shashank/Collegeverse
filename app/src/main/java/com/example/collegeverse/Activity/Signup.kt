package com.example.collegeverse.Activity

import android.app.AlertDialog
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
import com.example.collegeverse.Model.GenerateOTP
import com.example.collegeverse.Model.SignupUser
import com.example.collegeverse.R
import com.example.collegeverse.ViewModel.RegistrationViewModel
import com.example.collegeverse.databinding.SignUpUserBinding
import com.example.collegeverse.databinding.VerifyEmailBinding
import com.example.collegeverse.di.DaggerApplicationComponent


class Signup : Fragment(R.layout.sign_up_user) {

    lateinit var signUpUserBinding: SignUpUserBinding
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        signUpUserBinding= SignUpUserBinding.inflate(inflater)
        registrationViewModel=ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getRegistrtionRepo()).get(RegistrationViewModel::class.java)

        signUpUserBinding.SignUpButton.setOnClickListener(View.OnClickListener {

            // Intialising Variables:
            val Username: String = signUpUserBinding.editTextName.getText().toString()
            val Email: String = signUpUserBinding.editTextEmail.getText().toString().trim()
            val Password: String = signUpUserBinding.editTextPassword.getText().toString().trim()
            val create_password: String = signUpUserBinding.editTextReEnterPassword.getText().toString().trim()

            // Checking conditions:
            if (TextUtils.isEmpty(Username)) {
                signUpUserBinding.editTextName.setError("Username is required")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(Email)) {
                signUpUserBinding.editTextEmail.setError("Email is required")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(Password)) {
                signUpUserBinding.editTextPassword.setError("Password is required")
                return@OnClickListener
            }
            if (Password.length < 6) {
                signUpUserBinding.editTextPassword.setError("Password must be >= 6 characters")
                return@OnClickListener
            }
            if (Password != create_password) {
                signUpUserBinding.editTextReEnterPassword.setError("Recheck password")
                return@OnClickListener
            }

            // Verify Email :
            signUpUserBinding.progressCircularLayout.visibility=View.VISIBLE
            registrationViewModel.verifyEmail(GenerateOTP(Email))
            registrationViewModel.VerifyOTP.observe(viewLifecycleOwner){ response->

                signUpUserBinding.progressCircularLayout.visibility=View.INVISIBLE
                Toast.makeText(context,response.message, Toast.LENGTH_SHORT).show()
                if(response.success.equals("true")) {
                    // Dialog Box :
                    val update: AlertDialog.Builder = AlertDialog.Builder(it.getRootView().getContext())
                    val view: View = LayoutInflater.from(it.getRootView().getContext()).inflate(R.layout.verify_email, null)
                    var binding = VerifyEmailBinding.bind(view)

                    update.setView(view)
                    val alertDialog: AlertDialog = update.create()
                    alertDialog.setCanceledOnTouchOutside(false)
                    alertDialog.show()

                    binding.VerifyButton.setOnClickListener(View.OnClickListener {

                        if (binding.editTextOtp.text.length < 6) {
                            binding.editTextOtp.setError("OTP must be 6 digits")
                            return@OnClickListener
                        }

                            if(response.success.equals("true")&&response.token.equals(binding.editTextOtp.text.toString())) {
                                // SignUp Activity :

                                Toast.makeText(context,"OTP Verified", Toast.LENGTH_SHORT).show()
                                alertDialog.cancel()
                                signUpUserBinding.progressCircularLayout.setVisibility(View.VISIBLE)
                                SignUp(Username,Email,Password);
                            }
                            else{
                                Toast.makeText(context,"Wrong OTP", Toast.LENGTH_SHORT).show()
                            }
                    })
                }
            }
        })
        return signUpUserBinding.root
    }

    // SignUp Activity :
    fun SignUp(Username: String, Email: String, Password: String) {
        registrationViewModel.Signup(SignupUser(Username,Email,Password))
        registrationViewModel.SignupStatus.observe(viewLifecycleOwner){response->
            Log.d("123456",response.toString())
            Toast.makeText(context,response.message,Toast.LENGTH_SHORT).show()
            if(response.success.equals("true"))
            {
                registrationViewModel.SetToken(response.token, context!!)
                startActivity(Intent(context, MainActivity::class.java))
            }
            signUpUserBinding.progressCircularLayout.setVisibility(View.INVISIBLE);
        }
    }
}