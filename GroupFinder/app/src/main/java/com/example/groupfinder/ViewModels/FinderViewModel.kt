package com.example.groupfinder.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.groupfinder.Data.Common.*
import com.example.groupfinder.Data.DB.UserDatabase
import com.example.groupfinder.Data.UserRepo

class FinderViewModel(application: Application) : AndroidViewModel(application){
    private val repo: UserRepo = UserRepo(UserDatabase.getDatabase(application).userDataDao())
    var userMeetings: LiveData<List<UserMeetings>>
    var userClasses: LiveData<List<Classes>>
    var allUserContents: LiveData<List<Contents>>
    var userInfo: LiveData<UserData>

    init {
        userMeetings  = repo.getAllMeetings()
        userClasses = repo.getAllUserClasses()
        allUserContents = repo.gettAllContents()
        userInfo = repo.getUserData()
    }

    fun getAllMeetings(): LiveData<MutableList<UserMeetings>>{
        return userMeetings
    }
}