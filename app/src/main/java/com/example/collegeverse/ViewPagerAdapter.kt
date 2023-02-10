package com.example.collegeverse

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.collegeverse.Activity.Signin
import com.example.collegeverse.Activity.Signup

class ViewPagerAdapter(fm: FragmentActivity): FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        return  when (position) {
            0 -> Signin()
            1 ->  Signup()
            else
            ->  Signin()
        }
    }
}