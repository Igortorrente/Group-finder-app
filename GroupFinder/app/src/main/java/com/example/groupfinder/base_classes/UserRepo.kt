package com.example.groupfinder.base_classes

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class UserRepo(private val userDao: UserDao) : android.app.Application(){

    var modUserGroups: MutableLiveData<List<UserGroups>> = MutableLiveData<List<UserGroups>>()
    var modUserClasses: MutableLiveData<List<Classes>> = MutableLiveData<List<Classes>>()
    var modAllUserContents: MutableLiveData<List<Contents>> = MutableLiveData<List<Contents>>()
    var modUserInfo: MutableLiveData<UserData> = MutableLiveData<UserData>()

    val userGroups: LiveData<List<UserGroups>> get() = modUserGroups
    val userClasses: LiveData<List<Classes>> get() = modUserClasses
    val allUserContents: LiveData<List<Contents>> get() = modAllUserContents
    val userInfo: LiveData<UserData> get() = modUserInfo

    /* TODO methods getAll*:
    // Check if database are updated
    // if it are updated -> return userDao.getAll*
    // if Not -> get all database data with userDao.getAll*, fetch data, upsert data new data,
    // delete all data that is not in the fetch */
    // Meeting Queries
    @WorkerThread
    fun getAllMeetings(): LiveData<List<UserGroups>>{
        // TODO: Check if db are updated
        /*
        val groups = API.getUserGroups("oi")
        GlobalScope.launch(Dispatchers.IO){
            for (i in groups.iterator()) {
                Log.d("insert", i.toString())
                Log.d("insert", insertMeeting(i).toString())
                val bla = userDao.getAllMeeting()
                Log.d("insert", bla.value.toString())
            }
        }
        val bla =
        Log.d("meet", bla.value.toString())*/
        modUserGroups = MutableLiveData<List<UserGroups>>(API.getUserGroups("oi"))
        return userGroups
    }

    /* TODO methods {insert, delete}*:
    // Send insert method on API, get response
    // if Success -> add at database
    // if Fail -> send a toast to user
    // delete all data that is not in the fetch */
    @WorkerThread
    suspend fun insertMeeting(meeting: UserGroups): Long{
        return userDao.upsertMeeting(meeting)
    }

    @WorkerThread
    suspend fun deleteMeetings(meeting: UserGroups): Int {
        return userDao.deleteMeetings(meeting)
    }

    // Meeting content
    @WorkerThread
    fun getAllMeetingContent(id: Int): LiveData<List<Contents>> {
        return userDao.getAllMeetingContent(id)
    }

    @WorkerThread
    fun gettAllContents(): LiveData<List<Contents>> {
        modAllUserContents = MutableLiveData()
        return userDao.getAllContents()
    }

    @WorkerThread
    suspend fun insertMeetingsContents(content: Contents): Long{
        return userDao.upsertMeetContent(content)
    }

    @WorkerThread
    suspend fun deleteMeetingsContents(content: Contents): Int {
        return userDao.deleteMeetContents(content)
    }

    // Class Queries
    @WorkerThread
    fun getAllUserClasses(): LiveData<List<Classes>> {
        modUserClasses = MutableLiveData()
        return userDao.getAllUserClasses()
    }

    @WorkerThread
    suspend fun deleteUserClass(userClass: Classes): Int {
        return userDao.deleteUserClass(userClass)
    }

    @WorkerThread
    suspend fun insertUserClass(userClass: Classes): Long{
        return userDao.upsertUserClass(userClass)
    }

    // UserRepo Queries
    @WorkerThread
    fun getUserData(): LiveData<UserData>{
        modUserInfo = MutableLiveData()
        return userDao.getUserData()
    }

    @WorkerThread
    suspend fun insetUserData(UserData: UserData): Long{
        return userDao.upsertUserData(UserData)
    }
}

