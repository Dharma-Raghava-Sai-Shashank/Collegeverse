package com.example.collegeverse.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collegeverse.Repository.RegistrationRepo
import javax.inject.Inject

class RegistrationViewModelFactory@Inject constructor(var registrationRepo: RegistrationRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistrationViewModel(registrationRepo)as T
    }
}