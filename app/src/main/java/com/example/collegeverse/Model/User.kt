package com.example.collegeverse.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey val _id:String,val name:String,val email:String,var phone:String,var about:String,var image:String)