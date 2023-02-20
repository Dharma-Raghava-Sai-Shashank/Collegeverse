package com.example.collegeverse.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.collegeverse.Adapter.ViewPagerAdapter
import com.example.collegeverse.ViewModel.RegistrationViewModel
import com.example.collegeverse.databinding.ActivityRegistrationBinding
import com.example.collegeverse.di.DaggerApplicationComponent
import com.google.android.material.tabs.TabLayoutMediator


class Registration : AppCompatActivity() {

    lateinit var binding: ActivityRegistrationBinding
    lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        registrationViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getRegistrtionRepo()).get(RegistrationViewModel::class.java)

        setContentView(binding.root)

//        registrationViewModel.SetToken("",this)
        registrationViewModel.Check_Active(this)
        registrationViewModel.isActive.observe(this){
            if(it)
                startActivity(Intent(this, MainActivity::class.java))
        }

        // View Pager and TabLayout :
        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tablayout1, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "LOGIN"
                1 -> "SIGNUP"
                else->""
            }
        }.attach()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAffinity(this@Registration)
    }
}