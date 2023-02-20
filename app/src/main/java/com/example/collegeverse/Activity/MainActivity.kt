package com.example.collegeverse.Activity

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.collegeverse.*
import com.example.collegeverse.Fragment.*
import com.example.collegeverse.ViewModel.MainViewModel
import com.example.collegeverse.databinding.ActivityMainBinding
import com.example.collegeverse.db.Database
import com.example.collegeverse.di.DaggerApplicationComponent
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var toggle:ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    var database= Database()
    var a=1
    lateinit var Name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel= ViewModelProvider(this,
            DaggerApplicationComponent.builder().build().getMainRepo()).get(
            MainViewModel::class.java)


        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.blue))
        drawerLayout=findViewById(R.id.drawer)
        toggle= ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.visibility=View.INVISIBLE

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        database.GetToken(this)?.let { mainViewModel.getUserById(it) }
        mainViewModel.User.observe(this){
            Name=it.name
            binding.PersonName.text=it.name
            Glide.with(this).load(it.image).centerCrop().placeholder(R.drawable.profile).into(binding.PersonImager)
            replacefragment(Fragment_home())
        }

        // Navigation View:
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.Home -> {
                    binding.PersonName?.setText(Name)
                    replacefragment(Fragment_home())
                    a = 1
                }
                R.id.Chat -> {
                    binding.PersonName?.setText("CONNECTION")
                    replacefragment(Fragment_connection())
                    a = 2
                }
                R.id.Add -> {
                    binding.PersonName?.setText("ADD POST")
                    replacefragment(Fragment_Post())
                    a = 3
                }
                R.id.Favorite -> {
                    binding.PersonName?.setText("FAVOURITES")
                    replacefragment(Fragment_liked())
                    a = 4
                }
                R.id.Profile -> {
                    binding.PersonName?.setText("PROFILE")
                    replacefragment(Fragment_Profile())
                    a = 5
                }
            }
            true
        }
        
    }

    // Menu :
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        drawerLayout.openDrawer(Gravity.START)
        return true
    }

    // Changing of Fragments by Selection :
    fun replacefragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView4, fragment)
        fragmentTransaction.commit()
    }

    fun comment(view: View?) {
        replacefragment(Fragment_Comment())
        a=6
    }

    override fun onBackPressed() {
        if (a != 1) {
            a = 1
            binding.PersonName?.setText(Name)
            replacefragment(Fragment_home())
        } else {
            super.onBackPressed()
            ActivityCompat.finishAffinity(this@MainActivity)
        }
    }
}