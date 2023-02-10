package com.example.collegeverse.di

import com.example.collegeverse.ViewModel.RegistrationViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun getRegistrtionRepo():RegistrationViewModelFactory
}