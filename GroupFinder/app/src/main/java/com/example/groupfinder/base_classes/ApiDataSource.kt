package com.example.groupfinder.base_classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

// Fetches data from external server as LiveData to be handled by the Local DB

// Fortunately or unfortunately, this seems to be the "best" way to avoid callbacks,
// which would prevent proper integration with the Local DB

class ApiDataSource(
    private val apiService: ApiService = RetrofitInitializer().apiService()
) {
    private val _downloadedUserData = MutableLiveData<userData>()
    val downloadedUserData: LiveData<userData>
        get() = _downloadedUserData

    suspend fun fetchUserData(ra: Int) {
        val fetchedUserData = apiService
                .userData(ra)
                .await()

        _downloadedUserData.postValue(fetchedUserData)
    }

    private val _downloadedUserGroups = MutableLiveData<List<UserMeetings>>()
    val downloadedUserGroups: LiveData<List<UserMeetings>>
        get() = _downloadedUserGroups

    suspend fun fetchUserGroups(ra: Int) {
        val fetchedUserGroups = apiService
            .userGroups(ra)
            .await()

        _downloadedUserGroups.postValue(fetchedUserGroups)
    }

}