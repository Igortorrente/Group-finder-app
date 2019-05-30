package com.example.groupfinder.base_classes

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class UserRepo(private val userDao: UserDao) : android.app.Application(){

    var myDB: UserDatabase = UserDatabase.getDatabase(this)

    // TODO: Introduce Networking at all of this
    // Meeting Queries
    @WorkerThread
    fun getAllMeetings(): LiveData<MutableList<UserMeetings>>{
        return userDao.getAllMeeting()
    }

    @WorkerThread
    fun insertMeeting(meeting: UserMeetings): Long{
        return userDao.upsertMeeting(meeting)
    }

    @WorkerThread
    fun deleteMeetings(meeting: UserMeetings): Int {
        return userDao.deleteMeetings(meeting)
    }

    // Meeting content
    @WorkerThread
    fun getAllMeetingContent(id: Int): LiveData<MutableList<Contents>>{
        return userDao.getAllMeetingContent(id)
    }

    @WorkerThread
    fun gettAllContents():  LiveData<MutableList<Contents>>{
        return userDao.getAllContents()
    }

    @WorkerThread
    fun insertMeetingsContents(content: Contents): Long{
        return userDao.upsertMeetContent(content)
    }

    @WorkerThread
    fun deleteMeetingsContents(content: Contents): Int {
        return userDao.deleteMeetContents(content)
    }

    // Class Queries
    @WorkerThread
    fun getAllUserClasses(): LiveData<MutableList<Classes>>{
        return userDao.getAllUserClasses()
    }

    @WorkerThread
    fun deleteUserClass(userClass: Classes): Int {
        return userDao.deleteUserClass(userClass)
    }

    @WorkerThread
    fun insertUserClass(userClass: Classes): Long{
        return userDao.upsertUserClass(userClass)
    }

    // UserRepo Queries
    @WorkerThread
    fun getUserData(): LiveData<userData>{
        return userDao.getUserData()
    }

    @WorkerThread
    fun insetUserData(userData: userData): Long{
        return userDao.upsertUserData(userData)
    }
}

