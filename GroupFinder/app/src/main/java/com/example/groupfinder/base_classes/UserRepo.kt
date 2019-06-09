package com.example.groupfinder.base_classes

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class UserRepo(private val userDao: UserDao) : android.app.Application(){

    var myDB: UserDatabase = UserDatabase.getDatabase(this)

    // TODO: Introduce Networking at all of this
    // Meeting Queries
    fun getAllMeetings(): LiveData<List<UserMeetings>>{
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

