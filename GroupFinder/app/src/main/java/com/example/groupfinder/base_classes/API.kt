package com.example.groupfinder.base_classes

import com.example.groupfinder.R


class API (serverAddress: String){

    // TODO: Check this parameter
    fun getUserGroups(userID: String): List<groupItem>{
        // TODO: change this dummy return:
        var groups : List<groupItem> = emptyList()
        groups = groups + groupItem(-1, "FISICA III(F329)", " unicamp",
            "Em 5 minutos", "", "", R.drawable.gde, emptyList())
        groups = groups + groupItem(-1,"Calculo III", "unicamp",
            "Em 5 minutos", "", "", R.drawable.gde, emptyList())
        groups = groups + groupItem(-1,"Dummy", "unicamp",
            "Em 5 minutos", "", "", R.drawable.gde, emptyList())
        groups = groups + groupItem(-1,"Dummy2", "unicamp",
            "Em 5 minutos", "", "", R.drawable.gde, emptyList())

        return groups

    }
    fun getUserSugestions(userID: String): List<groupItem>{
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