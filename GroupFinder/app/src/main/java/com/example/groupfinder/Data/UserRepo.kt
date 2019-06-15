package com.example.groupfinder.Data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groupfinder.Data.api.API
import com.example.groupfinder.Data.database.UserDao
import com.example.groupfinder.Data.entities.Class
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups

class UserRepo(private val userDao: UserDao) : android.app.Application(){

    // ***************************************** //
    // TODO: Introduce Networking at all of this //
    // ***************************************** //

    private var modUserGroups = MutableLiveData<List<UserGroups>>()
    private var modUserClasses = MutableLiveData<List<Class>>()
    private var modAllUserContents = MutableLiveData<List<Content>>()
    private var modUserInfo = MutableLiveData<UserData>()

    private val userGroups: LiveData<List<UserGroups>> get() = modUserGroups
    private val userClass: LiveData<List<Class>> get() = modUserClasses
    private val allUserContent: LiveData<List<Content>> get() = modAllUserContents
    private val userInfo: LiveData<UserData> get() = modUserInfo

    // Group Queries
    fun getAllGroups(): LiveData<List<UserGroups>>{
        val groups = API.getUserGroups("oi")
        modUserGroups.value = groups
        return userGroups
    }

    @WorkerThread
    fun insertGroup(Group: UserGroups): Long{
        val groups = modUserGroups.value as MutableList<UserGroups>
        groups.add(Group)
        modUserGroups.postValue(groups)
        //userDao.insertGroup(Group)
        return 0
    }

    @WorkerThread
    fun updateGroup(Group: UserGroups): Int {
        return userDao.updateGroup(Group)
    }

    @WorkerThread
    fun deleteGroup(Group: UserGroups): Int {
        return userDao.deleteGroup(Group)
    }

    // Group content
    @WorkerThread
    fun getAllGroupContent(id: Int): LiveData<List<Content>>{
        return userDao.getAllGroupContent(id)
    }

    @WorkerThread
    fun gettAllContents():  LiveData<List<Content>>{
        return userDao.getAllContents()
    }

    @WorkerThread
    fun insertGroupContents(content: Content): Long{
        return userDao.insertGroupContents(content)
    }

    @WorkerThread
    fun deleteGroupContents(content: Content): Int {
        return userDao.deleteGroupContents(content)
    }

    @WorkerThread
    fun updateGroupContents(content: Content): Int {
        return userDao.updateGroupContents(content)
    }

    // Class Queries
    @WorkerThread
    fun getAllUserClasses(): LiveData<List<Class>>{
        return userDao.getAllUserClasses()
    }

    @WorkerThread
    fun deleteUserClass(userClass: Class): Int {
        return userDao.deleteUserClass(userClass)
    }

    @WorkerThread
    fun insertUserClass(userClass: Class): Long{
        return userDao.insertUserClass(userClass)
    }

    @WorkerThread
    fun updateUserClass(userClass: Class): Int {
        return userDao.updateUserClass(userClass)
    }

    // UserRepo Queries
    @WorkerThread
    fun getUserData(): LiveData<UserData>{
        // TODO: Replace
        modUserInfo = MutableLiveData()
        modUserInfo.postValue(UserData(213, "joão", "eng de ali", "123"))
        return userInfo
    }

    @WorkerThread
    fun updateUserData(UserData: UserData): Int {
        return userDao.updateUserData(UserData)
    }

    @WorkerThread
    fun insetUserData(UserData: UserData): Long{
        return userDao.insetUserData(UserData)
    }
}
