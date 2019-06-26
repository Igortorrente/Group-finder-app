package com.example.groupfinder.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.groupfinder.Data.UserRepo
import com.example.groupfinder.Data.database.UserDatabase
import com.example.groupfinder.Data.entities.Class
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups

class FinderViewModel(application: Application) : AndroidViewModel(application){
    private val repo: UserRepo = UserRepo(UserDatabase.getDatabase(application).userDataDao(), application)
    var userGroups: LiveData<List<UserGroups>>
    var userClass: LiveData<List<Class>>
    var allUserContent: LiveData<List<Content>>
    var userInfo: LiveData<UserData>
    var GroupsSearched: LiveData<List<UserGroups>> = repo.searchGroups

    init {
        userGroups  = repo.getAllGroups()
        userClass = repo.getAllUserClasses()
        allUserContent = repo.gettAllContents()
        userInfo = repo.getUserData()
    }

    fun insertGroup(Group: UserGroups): Long{
        return repo.insertGroup(Group)
    }

    fun searchGroups(key: String){
        repo.groupSearch(key)
    }
}