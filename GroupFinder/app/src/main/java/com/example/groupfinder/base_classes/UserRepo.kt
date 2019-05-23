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
        return userDao.insertMeeting(meeting)
    }

    @WorkerThread
    fun updateMeet(meeting: UserMeetings): Long{
        return userDao.updateMeet(meeting)
    }

    @WorkerThread
    fun deleteMeetings(meeting: UserMeetings): Long{
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
    fun insertMeetContents(content: Contents): Long{
        return userDao.insertMeetContents(content)
    }

    @WorkerThread
    fun deleteMeetContents(content: Contents): Long{
        return userDao.deleteMeetContents(content)
    }

    @WorkerThread
    fun updateMeetContents(content: Contents): Long{
        return userDao.updateMeetContents(content)
    }

    // Class Queries
    @WorkerThread
    fun getAllUserClasses(): LiveData<List<Classes>>{
        return userDao.getAllUserClasses()
    }

    @WorkerThread
    fun deleteUserClass(userClass: Classes): Long{
        return userDao.deleteUserClass(userClass)
    }

    @WorkerThread
    fun insertUserClass(userClass: Classes): Long{
        return userDao.insertUserClass(userClass)
    }

    @WorkerThread
    fun updateUserClass(userClass: Classes): Long{
        return userDao.updateUserClass(userClass)
    }

    // UserRepo Queries
    @WorkerThread
    fun getUserData(): LiveData<userData>{
        return userDao.getUserData()
    }

    @WorkerThread
    fun updateUserData(userData: userData): Long{
        return userDao.updateUserData(userData)
    }

    @WorkerThread
    fun insetUserData(userData: userData): Long{
        return userDao.insetUserData(userData)
    }
}

