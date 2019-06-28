package com.example.groupfinder.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.groupfinder.Data.UserRepo
import com.example.groupfinder.Data.database.UserDatabase
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups

class FinderViewModel(application: Application) : AndroidViewModel(application){
    private val repo: UserRepo = UserRepo(UserDatabase.getDatabase(application).userDataDao(), application)
    var userGroups: LiveData<List<UserGroups>>
    //var allUserContent: LiveData<List<Content>>
    var userInfo: LiveData<UserData>
    var GroupsSearched: LiveData<List<UserGroups>> = repo.searchGroups

    init {
        userGroups  = repo.getAllUserGroups()
        //allUserContent = repo.gettAllContents()
        userInfo = repo.getUserData()
    }

    fun updateUserData(userInfo: UserData): Int{
        return repo.updateUserData(userInfo)
    }

    fun updateGroup(Group: UserGroups): Int{
        return repo.updateGroup(Group)
    }

    fun getAllGroupContent(id: Int): LiveData<List<Content>>{
        return repo.getAllGroupContent(id)
    }

    fun insertGroupContents(content: Content): Long{
        return repo.insertGroupContents(content)
    }

    fun deleteGroupContents(content: Content): Int {
        return repo.deleteGroupContents(content)
    }

    fun updateGroupContents(content: Content): Int {
        return repo.updateGroupContents(content)
    }

    fun insertGroup(Group: UserGroups): Long{
        return repo.insertGroup(Group)
    }

    fun searchGroups(key: String){
        repo.groupSearch(key)
    }
}