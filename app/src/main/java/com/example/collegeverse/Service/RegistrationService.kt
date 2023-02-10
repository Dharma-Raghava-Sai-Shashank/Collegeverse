package com.example.collegeverse.Service

import com.example.collegeverse.Model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationService {

    @POST("user/signup")
    suspend fun signup(@Body signupUser:SignupUser): Response<Responser>

    @POST("user/signin")
    suspend fun signin(@Body signinUser: SigninUser): Response<Responser>

    @POST("user/verifyEmail")
    suspend fun verifyEmail(@Body generateOtp: GenerateOTP): Response<Responser>

    @POST("user/generateOtp")
    suspend fun generateOtp(@Body generateOtp: GenerateOTP): Response<Responser>

    @POST("user/verifyOtp")
    suspend fun verifyOtp(@Body verifyOtp: VerifyOTP): Response<Responser>

    @POST("user/resetPassword")
    suspend fun resetPassword(@Body resetPassword: ResetPassword): Response<Responser>
}