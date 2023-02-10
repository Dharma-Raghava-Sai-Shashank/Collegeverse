package com.example.collegeverse.Application

import android.app.Application
import com.example.collegeverse.di.ApplicationComponent
import com.example.collegeverse.di.DaggerApplicationComponent

class Application: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent=DaggerApplicationComponent.builder().build()

    }
}