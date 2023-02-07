package com.example.collegeverse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collegeverse.databinding.ActivityRegistrationBinding
import com.google.android.material.tabs.TabLayoutMediator

class Registration : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tablayout1, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "LOGIN"
                else -> "SIGNUP"
            }
        }.attach()
    }
}