package com.example.groupfinder.Data.api

import android.content.Context
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.Response

// Makes and handles calls to the Utils Service
class ApiHandler {
    companion object {

        private val userService: UserService = RetrofitInitializer().userService()
        private val groupService: GroupService = RetrofitInitializer().groupService()
        private val contentService: ContentService = RetrofitInitializer().contentService()

        private lateinit var context: Context

        // All of these functions are semi-synchronous (let's say that they are synchronous
        // within a Kotlin coroutine)

        // Response and/or return value neeeds to be handled within the coroutine

        // USER CALLS

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
            return userService.userGroups(ra)
        }

        fun userCreatedGroups(ra: Int): Deferred<Response<List<UserGroups>>> {
            return userService.userCreatedGroups(ra)
        }

        fun userData(ra: Int): Deferred<Response<UserData>> {
            return userService.userData(ra)
        }

        fun userUpdate(user: UserData): Deferred<Response<JsonObject>> {
            return userService.userUpdate(Utils.toUserArg(user))
        }

        // GROUP CALLS

        fun groupRegister(ra: Int, group: UserGroups): Deferred<Response<JsonObject>> {
            val response = groupService
                .groupRegister(ApiGroupArgument(ra, Utils.toGroupArg(group)))

            return response

        }

        fun groupEnroll(ra: Int, group: UserGroups): Deferred<Response<JsonObject>> {
            val response = groupService
                .groupEnroll(ApiEnrollArgument(ra, group.id))

            return response

        }

        fun groupUnenroll(ra: Int, group: UserGroups): Deferred<Response<JsonObject>> {
            val response = groupService
                .groupUnenroll(ApiEnrollArgument(ra, group.id))

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

        fun groupUpdate(group: UserGroups): Deferred<Response<JsonObject>> {
            val response = groupService
                .groupUpdate(ApiGroupUpdArgument(group.id, group.user_creator, group))

            return response
        }

        // CONTENT CALLS

        fun contentRegister(description: String, url: String, groupID: Int): Deferred<Response<JsonObject>> {
            return contentService.contentRegister(ContentGroupArg(description, url, groupID))
        }

        fun contentData(id: Int): Deferred<Response<Content>> {
            return contentService.contentData(id)
        }

        fun contentFindAll(): Deferred<Response<List<Content>>> {
            return contentService.contentFindAll()
        }

        fun contentDelete(id: Int): Deferred<Response<JsonObject>> {
            return contentService.contentDelete(id)
        }

        fun contentUpdate(id: Int, content: Content): Deferred<Response<JsonObject>> {
            return contentService.contentUpdate(id, Utils.toContentArg(content))
        }


        fun setContext(con: Context) {
            this.context = con
        }
    }

}
