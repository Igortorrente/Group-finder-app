package com.example.groupfinder.base_classes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class finderViewModel(application: Application) : AndroidViewModel(application){
    private val repo: UserRepo = UserRepo(UserDatabase.getDatabase(application).userDataDao())
    private lateinit var userMeetings: LiveData<MutableList<UserMeetings>>
    private lateinit var userClasses: LiveData<MutableList<Classes>>
    private lateinit var allUserContents: LiveData<MutableList<Contents>>
    private lateinit var userInfo: LiveData<userData>

    init {
        userMeetings  = repo.getAllMeetings()
        userClasses = repo.getAllUserClasses()
        allUserContents = repo.gettAllContents()
        userInfo = repo.getUserData()
    }

    fun getAllMeetings(): LiveData<MutableList<UserMeetings>>{
        return userMeetings
    }

    fun getUserClasses(): LiveData<MutableList<Classes>>{
        return userClasses
    }

    fun getuserInfo(): LiveData<userData>{
        return userInfo
    }

    fun getAllUserContent(): LiveData<MutableList<Contents>>{
        return allUserContents
    }
}