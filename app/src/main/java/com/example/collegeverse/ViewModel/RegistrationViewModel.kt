package com.example.collegeverse.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collegeverse.Model.*
import com.example.collegeverse.Repository.RegistrationRepo
import com.example.collegeverse.Utill.Constant
import kotlinx.coroutines.launch


class RegistrationViewModel(var registrationRepo: RegistrationRepo) : ViewModel() {

    // MutableLiveData :
    var _SignupStatus=MutableLiveData<Responser>()
    var _LoginStatus=MutableLiveData<Responser>()
    var _VerifyOTP=MutableLiveData<Responser>()
    var _isActive=MutableLiveData<Boolean>(false)

    // LiveData :
    var SignupStatus:LiveData<Responser> = _SignupStatus
    var LoginStatus:LiveData<Responser> = _LoginStatus
    var VerifyOTP:LiveData<Responser> = _VerifyOTP
    var isActive:LiveData<Boolean> = _isActive


    fun Signup(signupUser: SignupUser)
    {
        viewModelScope.launch {
            _SignupStatus.postValue(registrationRepo.Signup(signupUser))
        }
    }

    fun Signin(signinUser: SigninUser)
    {
        viewModelScope.launch {
            _LoginStatus.postValue(registrationRepo.Signin(signinUser))
        }
    }

    fun verifyEmail(generateOTP: GenerateOTP)
    {
        viewModelScope.launch {
            _VerifyOTP.postValue(registrationRepo.verifyEmail(generateOTP))
        }
    }

    fun generateOTP(generateOTP: GenerateOTP)
    {
        viewModelScope.launch {
            _VerifyOTP.postValue(registrationRepo.generateOTP(generateOTP))
        }
    }

    fun verifyOTP(otp: String) {
        viewModelScope.launch {
            _VerifyOTP.postValue(registrationRepo.verifyOTP(otp))
        }
    }

    fun resetPassword(password: String)
    {
        viewModelScope.launch {
            _VerifyOTP.postValue(registrationRepo.resetPassword(password))
        }
    }

    fun SetToken(token:String,context: Context)
    {
        viewModelScope.launch {
            registrationRepo.SetToken(token,context)
        }
        _isActive.postValue(true)
    }

    fun Check_Active(context: Context)
    {
           _isActive.postValue(registrationRepo.Check_Active(context))
    }
}