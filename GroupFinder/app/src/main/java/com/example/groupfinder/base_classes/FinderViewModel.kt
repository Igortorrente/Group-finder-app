package com.example.groupfinder.base_classes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FinderViewModel(application: Application) : AndroidViewModel(application){

    val repo: UserRepo = UserRepo(UserDatabase.getDatabase(application, viewModelScope).userDataDao())

    init {
        val db = UserDatabase.getDatabase(application, viewModelScope)
    }

    fun insert(meeting: UserGroups) = viewModelScope.launch {
        repo.insertMeeting(meeting)
    }

    fun getas() = viewModelScope.launch {
        repo.getAllMeetings()
    }
}