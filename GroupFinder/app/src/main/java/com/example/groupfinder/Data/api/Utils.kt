package com.example.groupfinder.Data.api

import android.app.AlertDialog
import android.content.Context
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups
import com.google.gson.annotations.SerializedName
import java.security.MessageDigest
import kotlin.experimental.and

data class UserGroupArg(
    @SerializedName("disciplina")
    val subject: String,
    @SerializedName("detalhes")
    val detail: String,
    @SerializedName("data_ini")
    val data_init: String,
    @SerializedName("data_fim")
    val data_end: String,
    @SerializedName("usuario_criador")
    val user_creator: Int,
    @SerializedName("local")
    val location_description: String
)

data class UserDataArg(
    val ra: Int,
    @SerializedName("nome")
    val name: String = "",
    @SerializedName("curso")
    val course: String = ""
)

data class ContentArg(
    @SerializedName("descicao")
    var description: String,
    val url: String
)

data class ContentGroupArg(
    @SerializedName("descicao")
    var description: String,
    val url: String,
    var group: Int
)

class ApiGroupArgument(
    val ra: Int,
    val groupArg: UserGroupArg
)

class ApiEnrollArgument(
    val ra: Int,
    val group: Int
)

class ApiGroupUpdArgument(
    val id_group: Int,
    val ra: Int,
    val group: UserGroups
)

class Utils (serverAddress: String){

    companion object {
        fun toGroupArg(group: UserGroups): UserGroupArg {
            return UserGroupArg(group.subject, group.detail, group.data_init, group.data_end, group.user_creator, group.location_description)
        }

        fun toUserArg(user: UserData): UserDataArg {
            return UserDataArg(user.ra, user.name, user.course)
        }

        fun toContentArg(content: Content): ContentArg {
            return ContentArg(content.description, content.url)
        }

        fun getSHA512hash(input: String): String {
            val md = MessageDigest.getInstance("SHA-512")
            val digest = md.digest(input.toByteArray())

            var str = String()

            for (i in digest.indices) {
                var l = (digest[i] and 0xFF.toByte()) + 0x100

                str += if (l > 0x100)
                    l.toString(16).substring(1)
                else
                    l.toString(16)
            }

            return str
        }

        fun showAlertDialog(context: Context, title: String, desc: String) {
            val dialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(desc)
                .setNeutralButton("Ok") {
                        dialog, _ ->
                    dialog.cancel()
                }
                .create()

            dialog.show()

        }

        fun getGroupIndex(groupsList: List<UserGroups>, id: Int): Int {
            if (!groupsList.isEmpty()) {
                for (i in groupsList.indices) {
                    if (groupsList[i].id == id)
                        return i
                }
            }

            return -1
        }
    }


}