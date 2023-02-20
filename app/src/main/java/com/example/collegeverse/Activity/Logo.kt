package com.example.collegeverse.Activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.collegeverse.R


class Logo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        // Getting address of other Activity:
        val intent = Intent(this, Registration::class.java)

        // Timer:
        object : CountDownTimer(1500, 1500) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                // Starting new Activity:
                startActivity(intent)
            }
        }.start()
    }
}