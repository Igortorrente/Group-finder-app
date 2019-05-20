package com.example.groupfinder.base_classes

import androidx.room.Room

class User(var name: String, var course: String, var ra: String,
           var email: String, var phone: String) : android.app.Application() {

    lateinit var myGroups: MutableList<userData>
    lateinit var myClasses: MutableList<Classes>
    lateinit var myDB: userDatabase
    init {
        myDB = Room.databaseBuilder(this, userDatabase::class.java,
            "$name.db").build()
    }

    fun loadUserInfo() {

    }

    fun storeUserInfo(){

    }
}