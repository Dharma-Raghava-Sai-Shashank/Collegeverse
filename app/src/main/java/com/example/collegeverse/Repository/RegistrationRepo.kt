package com.example.collegeverse.Repository

import android.content.Context
import com.example.collegeverse.Model.*
import com.example.collegeverse.db.Database
import com.example.collegeverse.Service.RegistrationService
import com.example.collegeverse.Utill.Constant
import javax.inject.Inject

class RegistrationRepo @Inject constructor(var registrationService: RegistrationService,var database: Database) {

    suspend fun Signup(signupUser: SignupUser): Responser?
    {
       return registrationService.signup(signupUser).body()
    }

    suspend fun Signin(signinUser: SigninUser): Responser?
    {
        return registrationService.signin(signinUser).body()
    }

    suspend fun verifyEmail(generateOTP: GenerateOTP):Responser?
    {
        return registrationService.verifyEmail(generateOTP).body()
    }

    suspend fun generateOTP(generateOTP: GenerateOTP):Responser?
    {
        return registrationService.generateOtp(generateOTP).body()
    }

    suspend fun verifyOTP(otp: String): Responser?
    {
        return registrationService.verifyOtp(VerifyOTP(Constant.email,otp)).body()
    }

    suspend fun resetPassword(password: String):Responser?
    {
        return registrationService.resetPassword(ResetPassword(Constant.email,password)).body()
    }

    suspend fun SetToken(token:String,context: Context)
    {
        database.SetToken(context,token)
    }

    fun Check_Active(context: Context):Boolean
    {
        if(database.GetToken(context)!="")
            return true
        return false
    }
}