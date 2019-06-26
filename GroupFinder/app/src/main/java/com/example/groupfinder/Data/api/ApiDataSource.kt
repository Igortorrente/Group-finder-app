package com.example.groupfinder.Data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groupfinder.Data.api.UserService
import com.example.groupfinder.Data.api.RetrofitInitializer
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups

// Fetches data from external server as LiveData to be handled by the Local DB

// Fortunately or unfortunately, this seems to be the "best" way to avoid callbacks,
// which would prevent proper integration with the Local DB

class ApiDataSource(
    private val userService: UserService = RetrofitInitializer().userService()
) {
    private val _downloadedUserData = MutableLiveData<UserData>()
    val downloadedUserData: LiveData<UserData>
        get() = _downloadedUserData

    suspend fun fetchUserData(ra: Int) {
        val fetchedUserData = userService
                .userData(ra)
                .await()

        _downloadedUserData.postValue(fetchedUserData)
    }

    private val _downloadedUserGroups = MutableLiveData<List<UserGroups>>()
    val downloadedUserGroups: LiveData<List<UserGroups>>
        get() = _downloadedUserGroups

    suspend fun fetchUserGroups(ra: Int) {
        val fetchedUserGroups = userService
            .userGroups(ra)
            .await()

        _downloadedUserGroups.postValue(fetchedUserGroups)
    }

}