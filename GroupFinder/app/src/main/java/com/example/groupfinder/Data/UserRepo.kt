package com.example.groupfinder.Data

import android.content.Context
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.groupfinder.Data.api.Utils
import com.example.groupfinder.Data.api.ApiGroupArgument
import com.example.groupfinder.Data.api.ApiHandler
import com.example.groupfinder.Data.api.ContentListArg
import com.example.groupfinder.Data.database.UserDao
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class UserRepo(private val userDao: UserDao, var context: Context) : android.app.Application(){

    // ***************************************** //
    // TODO: Introduce Networking at all of this //
    // ***************************************** //

    private var modUserGroups = MutableLiveData<List<UserGroups>>()


    private var modUserInfo = MutableLiveData<UserData>()
    private var modSearchGroups = MutableLiveData<List<UserGroups>>()

    private var modGroupContents = MutableLiveData<TreeMap<Int, List<Content>>>()
    private var modAllContents = MutableLiveData<TreeMap<Content, Int>>()

    private val appPrefs: Prefs = Prefs(context)

    private val groupContents: LiveData<TreeMap<Int, List<Content>>> get() = modGroupContents
    private val allContents: LiveData<TreeMap<Content, Int>> get() = modAllContents

    private val userGroups: LiveData<List<UserGroups>> get() = modUserGroups
    private val allContent: LiveData<TreeMap<Content, Int>> get() = modAllContents
    private val userInfo: LiveData<UserData> get() = modUserInfo
    val searchGroups: LiveData<List<UserGroups>> get() = modSearchGroups

    // Group Queries
    fun getAllUserGroups(): LiveData<List<UserGroups>>{
        //modUserGroups.value = emptyList()

        GlobalScope.launch {
            val groupsListDef = ApiHandler.userGroups(getCurrentRA())
            val ownedGroupsListDef = ApiHandler.userCreatedGroups(getCurrentRA())

            withContext(Dispatchers.Main) {
                try {
                    val groupsListResponse = groupsListDef.await()
                    val ownedGroupsListResponse = ownedGroupsListDef.await()

                    when {
                        groupsListResponse.code() == 200 && ownedGroupsListResponse.code() == 200  -> {
                            var groupsList = groupsListResponse.body()!! as MutableList<UserGroups>
                            groupsList.addAll(ownedGroupsListResponse.body()!!)
                            modUserGroups.value  = groupsList

                        }
                        else -> {
                            Utils.blank()
                        }
                    }
                } catch (t: Throwable) {
                    Toast.makeText(context, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }

        return userGroups
    }

    @WorkerThread
    fun insertGroup(Group: UserGroups): Long{
        GlobalScope.launch {
            val userRa = getCurrentRA()
            assert(userRa > 0 && userRa == Group.user_creator)
            val groupRegisterDef = ApiHandler.groupRegister(userRa, Group)

            withContext(Dispatchers.Main) {
                try {
                    val groupRegisterResponse = groupRegisterDef.await()
                    val responseCode = groupRegisterResponse.code()

                    when (responseCode) {
                        200 -> {
                            val groups = modUserGroups.value as MutableList<UserGroups>
                            groups.add(Group)
                            modUserGroups.value = groups
                        }
                        else -> {
                            Utils.showAlertDialog(context, "Erro ao criar grupo",
                                "Um erro desonhecido($responseCode) ocorreu ao criar o grupo.")
                        }
                    }
                }
                catch (t: Throwable) {
                    Utils.showAlertDialog(context, "Erro ao criar grupo", t.localizedMessage)
                }
            }
        }

        //userDao.insertGroup(Group)

        return 0
    }

    @WorkerThread
    fun updateGroup(Group: UserGroups): LiveData<List<UserGroups>> {
        GlobalScope.launch {
            val groupUpdateDef = ApiHandler.groupUpdate(Group)

            withContext(Dispatchers.Main) {
                try {
                    val groupUpdateResponse = groupUpdateDef.await()
                    val responseCode = groupUpdateResponse.code()

                    when (responseCode) {
                        200 -> {
                            val groupsList = modUserGroups.value as MutableList<UserGroups>
                            val groupIndex = Utils.getGroupIndex(groupsList, Group.id)

                            if (groupIndex >= 0) {
                                groupsList[groupIndex] = Group
                                modUserGroups.value = groupsList
                                Toast.makeText(context, "Sucesso !", Toast.LENGTH_SHORT).show()
                            }

                        }
                        else -> {
                            Utils.showAlertDialog(context, "Erro ao Atualizar Dados", "Um erro desconhecido ($responseCode) ocorreu ao tentar atualizar dados")
                        }
                    }
                }
                catch (t: Throwable) {
                    Utils.showAlertDialog(context, "Erro ao Atualizar Dados", t.localizedMessage)
                }
            }
        }

        return userGroups
    }

    @WorkerThread
    fun enrollGroup(Group: UserGroups): LiveData<List<UserGroups>> {
        GlobalScope.launch {
            val userRa = getCurrentRA()
            assert(userRa > 0 && userRa != Group.user_creator)
            val groupEnrollDef = ApiHandler.groupEnroll(userRa, Group)

            withContext(Dispatchers.Main) {
                try {
                    val groupRegisterResponse = groupEnrollDef.await()
                    val responseCode = groupRegisterResponse.code()

                    when (responseCode) {
                        200 -> {
                            val groups = modUserGroups.value as MutableList<UserGroups>
                            groups.add(Group)
                            modUserGroups.value = groups
                        }
                        else -> {
                            Utils.showAlertDialog(context, "Erro ao entrar em grupo",
                                "Um erro desonhecido($responseCode) ocorreu ao criar o grupo.")
                        }
                    }
                }
                catch (t: Throwable) {
                    Utils.showAlertDialog(context, "Erro ao entrar em grupo", t.localizedMessage)
                }
            }
        }

        return userGroups
    }

    @WorkerThread
    fun unenrollGroup(Group: UserGroups): LiveData<List<UserGroups>> {
        GlobalScope.launch {
            val userRa = getCurrentRA()
            assert(userRa > 0 && userRa != Group.user_creator)
            val groupUnenrollDef = ApiHandler.groupUnenroll(userRa, Group)

            withContext(Dispatchers.Main) {
                try {
                    val groupRegisterResponse = groupUnenrollDef.await()
                    val responseCode = groupRegisterResponse.code()

                    when (responseCode) {
                        200 -> {
                            val groupsList = modUserGroups.value as MutableList<UserGroups>
                            val groupIndex = Utils.getGroupIndex(groupsList, Group.id)

                            if (groupIndex >= 0) {
                                groupsList.removeAt(groupIndex)
                                modUserGroups.value = groupsList
                                Toast.makeText(context, "Sucesso !", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else -> {
                            Utils.showAlertDialog(context, "Erro ao sair de  grupo",
                                "Um erro desonhecido($responseCode) ocorreu ao criar o grupo.")
                        }
                    }
                }
                catch (t: Throwable) {
                    Utils.showAlertDialog(context, "Erro ao sair de em grupo", t.localizedMessage)
                }
            }
        }

        return userGroups
    }

    // Group content

    @WorkerThread
    fun contentFindAllByGroup(id: Int) {
        GlobalScope.launch {
            val contentAddDef = ApiHandler.contentFindAllByGroup(id)

            withContext(Dispatchers.Main) {
                try {
                    val contentListResponse = contentAddDef.await()
                    val responseCode = contentListResponse.code()

                    when (responseCode) {
                        200 -> {
                            val contentGroupMap = modGroupContents.value

                            contentGroupMap!![id] = contentListResponse.body()!!.conteudos

                        }
                        else -> {
                            Toast.makeText(context, "Erro: $responseCode", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (t: Throwable) {
                    Toast.makeText(context, "Erro: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @WorkerThread
    fun insertGroupContents(content: Content, groupId: Int) {
        GlobalScope.launch {
            val contentAddDef = ApiHandler.contentRegister(content.description, content.url, groupId)

            withContext(Dispatchers.Main) {
                try {
                    val contentAddResponse = contentAddDef.await()
                    val responseCode = contentAddResponse.code()

                    when (responseCode) {
                        200 -> {
                            // TODO: IMPLEMENT CONTENT ADDITION/REGISTERING SOMEHOW

                        }
                        else -> {
                            Toast.makeText(context, "Erro: $responseCode", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (t: Throwable) {
                    Toast.makeText(context, "Erro: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @WorkerThread
    fun deleteGroupContents(content: Content) {
        GlobalScope.launch {
            val contentDelDef = ApiHandler.contentDelete(content.id)

            withContext(Dispatchers.Main) {
                try {
                    val contentDelResponse = contentDelDef.await()
                    val responseCode = contentDelResponse.code()

                    when (responseCode) {
                        200 -> {
                            // TODO: IMPLEMENT CONTENT DELETION/REMOVAL SOMEHOW

                        }
                        else -> {
                            Toast.makeText(context, "Erro: $responseCode", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (t: Throwable) {
                    Toast.makeText(context, "Erro: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @WorkerThread
    fun updateGroupContents(content: Content) {
        GlobalScope.launch {
            val contentUpdDef = ApiHandler.contentUpdate(content.id, content)

            withContext(Dispatchers.Main) {
                try {
                    val contentUpdResponse = contentUpdDef.await()
                    val responseCode = contentUpdResponse.code()

                    when (responseCode) {
                        200 -> {
                            // TODO: IMPLEMENT CONTENT UPDATING SOMEHOW

                        }
                        else -> {
                            Toast.makeText(context, "Erro: $responseCode", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (t: Throwable) {
                    Toast.makeText(context, "Erro: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun groupSearch(key: String){
        val searchAll = key.isEmpty()

        GlobalScope.launch {
            val searchDef = if (searchAll) ApiHandler.groupFindAll() else ApiHandler.groupFindBySubject(key)

            withContext(Dispatchers.Main) {
                try {
                    val searchResponse = searchDef.await()
                    val responseCode = searchResponse.code()

                    when (responseCode) {
                        200 -> {
                            modSearchGroups.value = searchResponse.body()
                        }
                        404 -> {
                            modSearchGroups.value = emptyList()
                        }
                        else -> {
                            modSearchGroups.value = emptyList()
                            Toast.makeText(context, "Sem Resultados", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (t: Throwable) {
                    modSearchGroups.value = emptyList()
                    Toast.makeText(context, "Sem Resultados", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    // UserRepo Queries
    @WorkerThread
    fun getUserData(): LiveData<UserData>{
        // TODO: Replace
        //modUserInfo = MutableLiveData()

        val userRa = getCurrentRA()

        if (userRa > 0) {
            GlobalScope.launch {

                val userDataDef = ApiHandler.userData(userRa)

                withContext(Dispatchers.Main) {
                    val userDataResponse = userDataDef.await()

                    val responseCode = userDataResponse.code()

                    if (responseCode == 200) {
                        userDataResponse.body().let {
                            modUserInfo.value = it
                        }
                    }
                }
            }
        }

        return userInfo

    }

    fun getCurrentRA(): Int {
        return appPrefs.userRa
    }

    fun setCurrentRA(userRa: Int) {
        appPrefs.userRa = userRa
    }

    @WorkerThread
    fun updateUserData(UserData: UserData) {
        GlobalScope.launch {
            val userUpdateDef = ApiHandler.userUpdate(UserData)

            withContext(Dispatchers.Main) {
                try {
                    val userUpdateResponse = userUpdateDef.await()
                    val responseCode = userUpdateResponse.code()

                    when (responseCode) {
                        200 -> {
                            modUserInfo.value = UserData
                            Toast.makeText(context, "Sucesso !", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Utils.showAlertDialog(context, "Erro ao Atualizar Dados", "Um erro desconhecido ($responseCode) ocorreu ao tentar atualizar dados")
                        }
                    }
                }
                catch (t: Throwable) {
                    Utils.showAlertDialog(context, "Erro ao Atualizar Dados", t.localizedMessage)
                }
            }
        }
    }

}

