package com.example.groupfinder.Data.api

import android.app.AlertDialog
import android.content.Context
import com.example.groupfinder.Data.entities.UserGroups
import com.example.groupfinder.R
import java.security.MessageDigest
import kotlin.experimental.and


class ApiGroupArgument(
    val ra: Int,
    val group: UserGroups
)

class API (serverAddress: String){

    companion object {
        // TODO: Check this parameter
        fun getUserGroups(userID: String): List<UserGroups>{
            // TODO: change this dummy return:
            var groups : List<UserGroups> = emptyList()
            groups = groups + UserGroups(
                1, "FISICA III(F329)", " unicamp",
                10, 12, 15, 100, "oi"
            )
            groups = groups + UserGroups(
                2, "Calculo III", "unicamp",
                10, 12, 15, 100, "oi"
            )
            groups = groups + UserGroups(
                3, "Dummy", "unicamp",
                10, 12, 15, 100, "oi"
            )
            groups = groups + UserGroups(
                4, "Dummy2", "unicamp",
                10, 12, 15, 100, "oi"
            )

            return groups
        }
        fun getUserSugestions(userID: String): List<UserGroups>{
            // TODO: change this dummy return:
            var groups : List<UserGroups> = emptyList()
            groups = groups + UserGroups(
                1, "FISICA III(F329)", " unicamp",
                10, 12, 15, 100, "oi"
            )
            groups = groups + UserGroups(
                2, "Calculo III", "unicamp",
                10, 12, 15, 100, "oi"
            )
            groups = groups + UserGroups(
                3, "Dummy", "unicamp",
                10, 12, 15, 100, "oi"
            )
            groups = groups + UserGroups(
                4, "Dummy2", "unicamp",
                10, 12, 15, 100, "oi"
            )

            return groups
        }

        fun getSHA512hash(input: String): String {
            val md = MessageDigest.getInstance("SHA-512")
            val digest = md.digest(input.toByteArray())

            val sb = StringBuilder()

            for (i in digest.indices) {
                val i = (digest[i] and 0xFF.toByte()) + 0x100
                sb.append(i.toString(16).substring(1))
            }

            return sb.toString()
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
    }

    // Fetches a single group from external server
    // Should be used to update single group view right after editing a group
    fun getGroup(groupID: Int): groupItem {
        return groupItem(-1,"MC102", " unicamp",
            "Em 5 minutos", "", "", R.drawable.gde, emptyList())
    }

    fun createGroup(mainSubject: String, place: String, timeToBegin: Int, timeToEnd: Int, details: String): Int {
        return -1
    }

    fun updateGroup(groupID: Int, mainSubject: String, place: String, timeToBegin: Int, timeToEnd: Int, details: String, contents: List<contentItem>): Boolean {
        return false
    }

    fun findGroups(mainSubject: String): List<groupItem>{
        // TODO: change this dummy return:
        var groups : List<groupItem> = emptyList()
        groups = groups + groupItem(-1,"MC102", " unicamp",
            "Em 5 minutos", "", "", R.drawable.gde, emptyList())
        groups = groups + groupItem(-1,"Estrutura de dados(MC202)", "unicamp",
            "Em 5 minutos", "", "", R.drawable.gde, emptyList())
        groups = groups + groupItem(-1,"Dummy", "unicamp",
            "Em 5 minutos", "", "", R.drawable.gde, emptyList())
        groups = groups + groupItem(-1,"Dummy2", "unicamp",
            "Em 5 minutos", "", "", R.drawable.gde, emptyList())

        return groups
    }


}