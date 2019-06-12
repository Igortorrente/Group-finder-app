package com.example.groupfinder.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.groupfinder.Data.Common.*
import com.example.groupfinder.Data.DB.UserDatabase
import com.example.groupfinder.Data.UserRepo

class FinderViewModel(application: Application) : AndroidViewModel(application){
    private val repo: UserRepo = UserRepo(UserDatabase.getDatabase(application).userDataDao())
    var userGroups: LiveData<List<UserGroups>>
    var userClass: LiveData<List<Class>>
    var allUserContent: LiveData<List<Content>>
    var userInfo: LiveData<UserData>

    init {
        userGroups  = repo.getAllGroups()
        userClass = repo.getAllUserClasses()
        allUserContent = repo.gettAllContents()
        userInfo = repo.getUserData()
    }
}