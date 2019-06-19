package com.example.groupfinder.Data.api

import android.content.Context
import android.service.autofill.UserData
import android.widget.Toast
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiUser(val ra: Int, val nome: String? = "", val curso: String? = "", val senha:String)
class ApiGroup()

// Makes and handles calls to the API Service 
class ApiHandler(
    private val apiService: ApiService = RetrofitInitializer().apiService()
) {

    private lateinit var context: Context

    // All of these functions are semi-synchronous (let's say that they are synchronous
    // within a Kotlin coroutine)

    // Response and/or return value neeeds to be handled within the coroutine

    suspend fun userAuth(user: userData): Response<JsonObject> {
        val response = apiService
            .userAuth(user)
            .await()

        return response

    }

    suspend fun userRegister(user: userData): Response<JsonObject>  {
        val response = apiService
            .userRegister(user)
            .await()

        return response

    }

    fun userGroups(ra: Int) : Deferred<Response<List<UserMeetings>>> {
        return apiService.userGroupsResponse(ra)
    }

    fun userData(ra: Int) : Deferred<Response<userData>> {
        return apiService.userDataResponse(ra)
    }

    suspend fun groupRegister(groupArgument: ApiGroupArgument): Response<JsonObject> {
        val response = apiService
            .groupRegister(groupArgument)
            .await()

        return response

    }

    suspend fun groupData(id: Int): UserMeetings {
        val group = apiService
            .groupData(id)
            .await()

        return group
    }

    suspend fun groupFindBySubject(subId: Int): List<UserMeetings> {
        val searchResult = apiService
            .groupFindBySubject(subId)
            .await()

        return searchResult
    }

    fun setContext(con: Context) {
        this.context = con
    }

}
