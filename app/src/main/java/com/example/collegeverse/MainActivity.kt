package com.example.collegeverse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
    }
    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAffinity(this@MainActivity)
    }
}