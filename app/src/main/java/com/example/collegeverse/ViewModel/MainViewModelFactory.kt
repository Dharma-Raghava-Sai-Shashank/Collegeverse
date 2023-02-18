package com.example.collegeverse.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.collegeverse.Repository.MainRepo
import com.example.collegeverse.Repository.RegistrationRepo
import javax.inject.Inject

class MainViewModelFactory@Inject constructor(var mainRepo: MainRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepo)as T
    }
}