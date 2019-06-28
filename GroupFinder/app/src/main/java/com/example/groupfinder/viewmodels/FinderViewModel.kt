package com.example.groupfinder.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.groupfinder.Data.Prefs
import com.example.groupfinder.Data.UserRepo
import com.example.groupfinder.Data.database.UserDatabase
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups

class FinderViewModel(application: Application) : AndroidViewModel(application){
    var context: Context = application
    private val repo: UserRepo = UserRepo(UserDatabase.getDatabase(application).userDataDao(), context)
    var userGroups: LiveData<List<UserGroups>>
    //var allContent: LiveData<List<Content>>
    var userInfo: LiveData<UserData>
    var GroupsSearched: LiveData<List<UserGroups>> = repo.searchGroups


    init {
        userGroups  = repo.getAllUserGroups()
        //allContent = repo.getAllContents()
        userInfo = repo.getUserData()
    }

    fun getCurrentRA(): Int {
        return repo.getCurrentRA()
    }

    fun setCurrentRA(userRa: Int) {
        repo.setCurrentRA(userRa)
    }

    // USER CALLS
    fun updateUserData(user: UserData) {
        repo.updateUserData(user)
    }

    // GROUP CALLS
    fun insertGroup(Group: UserGroups): Long{
        return repo.insertGroup(Group)
    }

    fun updateGroup(Group: UserGroups) {
        userGroups  = repo.updateGroup(Group)
    }

    fun enrollGroup(Group: UserGroups) {
        userGroups = repo.enrollGroup(Group)
    }

    fun unenrollGroup(Group: UserGroups) {
        userGroups = repo.unenrollGroup(Group)
    }

    fun searchGroups(key: String){
        repo.groupSearch(key)
    }

    // CONTENT CALLS
    fun contentAdd(Group: UserGroups, content: Content) {
        repo.insertGroupContents(content, Group.id)
    }

    fun contentUpdate(content: Content) {
        repo.updateGroupContents(content)
    }



    fun changeContext(context: Context) {
        this.context = context
        repo.context = context
    }
}