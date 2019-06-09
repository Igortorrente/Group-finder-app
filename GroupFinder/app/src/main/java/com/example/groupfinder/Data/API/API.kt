package com.example.groupfinder.Data.API

import com.example.groupfinder.Data.Common.UserMeetings
import com.example.groupfinder.R


class API (serverAddress: String){

    companion object {
        // TODO: Check this parameter
        fun getUserGroups(userID: String): List<UserMeetings>{
            // TODO: change this dummy return:
            var groups : List<UserMeetings> = emptyList()
            groups = groups + UserMeetings(1, "FISICA III(F329)", " unicamp",
                10, 12, 15, 100, "oi")
            groups = groups + UserMeetings(2, "Calculo III", "unicamp",
                10, 12, 15, 100, "oi")
            groups = groups + UserMeetings(3, "Dummy", "unicamp",
                10, 12, 15, 100, "oi")
            groups = groups + UserMeetings(4, "Dummy2", "unicamp",
                10, 12, 15, 100, "oi")

            return groups
        }
        fun getUserSugestions(userID: String): List<UserMeetings>{
            // TODO: change this dummy return:
            var groups : List<UserMeetings> = emptyList()
            groups = groups + UserMeetings(1, "FISICA III(F329)", " unicamp",
                10, 12, 15, 100, "oi")
            groups = groups + UserMeetings(2, "Calculo III", "unicamp",
                10, 12, 15, 100, "oi")
            groups = groups + UserMeetings(3, "Dummy", "unicamp",
                10, 12, 15, 100, "oi")
            groups = groups + UserMeetings(4, "Dummy2", "unicamp",
                10, 12, 15, 100, "oi")

            return groups
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