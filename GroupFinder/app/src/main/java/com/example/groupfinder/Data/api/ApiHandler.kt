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

        fun groupRegister(groupArgument: ApiGroupArgument): Deferred<Response<JsonObject>> {
            val response = groupService
                .groupRegister(groupArgument)

            return response

        }

        fun groupEnroll(enrollArgument: ApiEnrollArgument): Deferred<Response<JsonObject>> {
            val response = groupService
                .groupEnroll(enrollArgument)

            return response

        }

        fun groupData(id: Int): Deferred<Response<UserGroups>> {
            val response = groupService
                .groupData(id)

            return response
        }

        fun groupFindAll(): Deferred<Response<List<UserGroups>>> {
            val response = groupService
                .groupFindAll()

            return response
        }

        fun groupFindBySubject(sub: String): Deferred<Response<List<UserGroups>>> {
            val response = groupService
                .groupFindBySubject(sub)

            return response
        }

        fun setContext(con: Context) {
            this.context = con
        }
    }

}
