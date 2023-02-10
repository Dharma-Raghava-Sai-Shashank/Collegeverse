package com.example.collegeverse.db

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class Database @Inject constructor(){
    lateinit var sharedPreferences:SharedPreferences
    lateinit var editor:SharedPreferences.Editor
    fun SetToken(context: Context,token:String)
    {
        sharedPreferences=context.getSharedPreferences("App",0)
        editor=sharedPreferences.edit()

        editor.putString("token",token)
        editor.commit()
    }
    fun GetToken(context: Context): String?
    {
        sharedPreferences=context.getSharedPreferences("App",0)
        return sharedPreferences.getString("token","")
    }
}