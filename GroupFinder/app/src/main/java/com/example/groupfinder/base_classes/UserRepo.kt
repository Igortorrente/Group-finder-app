package com.example.groupfinder.base_classes

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserRepo(private val userDao: UserDao) : android.app.Application(){

    // TODO: Introduce Networking at all of this
    private var modUserMeetings = MutableLiveData<List<UserMeetings>>()
    private var modUserClasses = MutableLiveData<List<Classes>>()
    private var modAllUserContents = MutableLiveData<List<Contents>>()
    private var modUserInfo = MutableLiveData<UserData>()

    private val userMeetings: LiveData<List<UserMeetings>> get() = modUserMeetings
    private val userClasses: LiveData<List<Classes>> get() = modUserClasses
    private val allUserContents: LiveData<List<Contents>> get() = modAllUserContents
    private val userInfo: LiveData<UserData> get() = modUserInfo

    // Meeting Queries
    fun getAllMeetings(): LiveData<List<UserMeetings>>{
        val meets = API.getUserGroups("oi")
        modUserMeetings.value = meets
        return userMeetings
    }

    @WorkerThread
    fun insertMeeting(meeting: UserMeetings): Long{
        return userDao.insertMeeting(meeting)
    }

    @WorkerThread
    fun updateMeet(meeting: UserMeetings): Int {
        return userDao.updateMeet(meeting)
    }

    @WorkerThread
    fun deleteMeetings(meeting: UserMeetings): Int {
        return userDao.deleteMeetings(meeting)
    }

    // Meeting content
    @WorkerThread
    fun getAllMeetingContent(id: Int): LiveData<List<Contents>>{
        return userDao.getAllMeetingContent(id)
    }

    @WorkerThread
    fun gettAllContents():  LiveData<List<Contents>>{
        return userDao.getAllContents()
    }

    @WorkerThread
    fun insertMeetingsContents(content: Contents): Long{
        return userDao.insertMeetContents(content)
    }

    @WorkerThread
    fun deleteMeetingsContents(content: Contents): Int {
        return userDao.deleteMeetContents(content)
    }

    @WorkerThread
    fun updateMeetingsContents(content: Contents): Int {
        return userDao.updateMeetContents(content)
    }

    // Class Queries
    @WorkerThread
    fun getAllUserClasses(): LiveData<List<Classes>>{
        return userDao.getAllUserClasses()
    }

    @WorkerThread
    fun deleteUserClass(userClass: Classes): Int {
        return userDao.deleteUserClass(userClass)
    }

    @WorkerThread
    fun insertUserClass(userClass: Classes): Long{
        return userDao.insertUserClass(userClass)
    }

    @WorkerThread
    fun updateUserClass(userClass: Classes): Int {
        return userDao.updateUserClass(userClass)
    }

    // UserRepo Queries
    @WorkerThread
    fun getUserData(): LiveData<UserData>{
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

