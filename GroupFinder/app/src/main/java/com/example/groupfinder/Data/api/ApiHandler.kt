package com.example.groupfinder.Data.api

import android.content.Context
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response

// Makes and handles calls to the API Service 
class ApiHandler {
    companion object {

        private val userService: UserService = RetrofitInitializer().userService()
        private val groupService: GroupService = RetrofitInitializer().groupService()
        private val contentService: ContentService = RetrofitInitializer().contentService()

        private lateinit var context: Context

        // All of these functions are semi-synchronous (let's say that they are synchronous
        // within a Kotlin coroutine)

        // Response and/or return value neeeds to be handled within the coroutine

        fun userAuth(user: UserData): Deferred<Response<JsonObject>> {
            val response = userService
                .userAuth(user)

            return response

        }

        fun userRegister(user: UserData): Deferred<Response<JsonObject>> {
            val response = userService
                .userRegister(user)

            return response

        }

        fun userGroups(ra: Int): Deferred<Response<List<UserGroups>>> {
            return userService.userGroupsResponse(ra)
        }

        fun userData(ra: Int): Deferred<Response<UserData>> {
            return userService.userDataResponse(ra)
        }

        suspend fun groupRegister(groupArgument: ApiGroupArgument): Response<JsonObject> {
            val response = groupService
                .groupRegister(groupArgument)
                .await()

            return response

        }

        suspend fun groupData(id: Int): UserGroups {
            val group = groupService
                .groupData(id)
                .await()

            return group
        }

        suspend fun groupFindBySubject(subId: Int): List<UserGroups> {
            val searchResult = groupService
                .groupFindBySubject(subId)
                .await()

            return searchResult
        }

        fun setContext(con: Context) {
            this.context = con
        }
    }

}
