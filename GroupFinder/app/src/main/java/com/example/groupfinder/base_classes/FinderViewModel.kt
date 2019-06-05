package com.example.groupfinder.base_classes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class FinderViewModel(application: Application) : AndroidViewModel(application){
    private val repo: UserRepo = UserRepo(UserDatabase.getDatabase(application).userDataDao())
    var userMeetings: LiveData<List<UserMeetings>>
    var userClasses: LiveData<List<Classes>>
    var allUserContents: LiveData<List<Contents>>
    var userInfo: LiveData<userData>

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