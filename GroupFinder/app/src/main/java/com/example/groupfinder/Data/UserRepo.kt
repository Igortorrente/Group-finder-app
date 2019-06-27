package com.example.groupfinder.Data

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groupfinder.Data.api.API
import com.example.groupfinder.Data.api.ApiGroupArgument
import com.example.groupfinder.Data.api.ApiHandler
import com.example.groupfinder.Data.database.UserDao
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserRepo(private val userDao: UserDao, private val context: Context) : android.app.Application(){

    // ***************************************** //
    // TODO: Introduce Networking at all of this //
    // ***************************************** //

    private var modUserGroups = MutableLiveData<List<UserGroups>>()

    private var modAllUserContents = MutableLiveData<List<Content>>()
    private var modUserInfo = MutableLiveData<UserData>()
    private var modSearchGroups = MutableLiveData<List<UserGroups>>()

    private val userGroups: LiveData<List<UserGroups>> get() = modUserGroups
    private val allUserContent: LiveData<List<Content>> get() = modAllUserContents
    private val userInfo: LiveData<UserData> get() = modUserInfo
    val searchGroups: LiveData<List<UserGroups>> get() = modSearchGroups

    // Group Queries
    fun getAllUserGroups(): LiveData<List<UserGroups>>{
        //modUserGroups.value = emptyList()

        GlobalScope.launch {
            val groupsListDef = ApiHandler.userGroups(177953)

            withContext(Dispatchers.Main) {
                try {
                    val groupsListResponse = groupsListDef.await()

                    when {
                        groupsListResponse.code() == 200 -> {
                            groupsListResponse.body().let {
                                modUserGroups.value = it
                            }
                        }
                        else -> {
                            //modUserGroups.value = emptyList()
                        }
                    }
                } catch (t: Throwable) {
                    //modUserGroups.value = emptyList()
                }
            }

        }

        return userGroups
    }

    @WorkerThread
    fun insertGroup(Group: UserGroups): Long{
        GlobalScope.launch {
            val userRa = Prefs(context).userRa
            val groupRegisterDef = ApiHandler.groupRegister(ApiGroupArgument(userRa, Group))

            withContext(Dispatchers.Main) {
                try {
                    val groupRegisterResponse = groupRegisterDef.await()
                    val responseCode = groupRegisterResponse.code()

                    when (responseCode) {
                        200 -> {
                            val groups = modUserGroups.value as MutableList<UserGroups>
                            groups.add(Group)
                            modUserGroups.postValue(groups)
                        }
                        else -> {
                            API.showAlertDialog(context, "Erro ao criar grupo",
                                "Um erro desonhecido($responseCode) ocorreu ao criar o grupo.")
                        }
                    }
                }
                catch (t: Throwable) {
                    API.showAlertDialog(context, "Erro ao criar grupo", t.localizedMessage)
                }
            }
        }

        //userDao.insertGroup(Group)

        return 0
    }

    @WorkerThread
    fun updateGroup(Group: UserGroups): Int {
        return userDao.updateGroup(Group)
    }

    // Group content
    @WorkerThread
    fun getAllGroupContent(id: Int): LiveData<List<Content>>{
        return userDao.getAllGroupContent(id)
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

    fun groupSearch(key: String){
        val groups = listOf<UserGroups>(UserGroups(0,"lolzinho diamante", "lolzinho diamante", 0,0,
            0,"oi"),
            UserGroups(0,"lolzinho chalenger", "lolzinho chalenger", 0,0,
                0,"oi"),
            UserGroups(0,"lolzinho prata", "lolzinho prata", 0,0,
                0,"oi"),
            UserGroups(0,"lolzinho diamante", "lolzinho diamante", 0,0,
                15,"oi")
            )
        modSearchGroups.value = groups

    }

    // UserRepo Queries
    @WorkerThread
    fun getUserData(): LiveData<UserData>{
        // TODO: Replace
        //modUserInfo = MutableLiveData()

        val userRa = Prefs(context).userRa

        if (userRa > 0) {
            GlobalScope.launch {

                val userDataDef = ApiHandler.userData(userRa)

                withContext(Dispatchers.Main) {
                    val userDataResponse = userDataDef.await()

                    val responseCode = userDataResponse.code()

                    if (responseCode == 200) {
                        userDataResponse.body().let {
                            modUserInfo.postValue(it)
                        }
                    }
                }
            }
        }

        return userInfo

    }

    @WorkerThread
    fun updateUserData(UserData: UserData): Int {
        return userDao.updateUserData(UserData)
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }

    companion object {
        var prefs: Prefs? = null
    }
}

