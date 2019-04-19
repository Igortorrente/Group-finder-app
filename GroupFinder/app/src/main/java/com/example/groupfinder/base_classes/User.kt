package com.example.groupfinder.base_classes

class User(var name: String, var course: String, var ra: String,
           var email: String, var phone: String) {

    lateinit var myGroups: MutableList<groupItem>

    init {

    }

    fun loadUserInfo(){

    }

    fun storeUserInfo(){

    }
}