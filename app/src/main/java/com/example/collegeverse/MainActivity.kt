package com.example.collegeverse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.collegeverse.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var toggle:ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView:NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.blue))
        drawerLayout=findViewById(R.id.drawer)
        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        toggle.syncState()
        
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
    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCompat.finishAffinity(this@MainActivity)
    }
}