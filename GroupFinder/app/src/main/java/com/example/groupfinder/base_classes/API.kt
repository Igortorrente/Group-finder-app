package com.example.groupfinder.base_classes

import com.example.groupfinder.R


class API (serverAddress: String){
    companion object {
        // TODO: Check this parameter
        fun getUserGroups(userID: String): List<groupItem>{
            // TODO: change this dummy return:
            var groups : List<groupItem> = emptyList()
            groups = groups + groupItem("FISICA III(F329)", " unicamp",
                "Em 5 minutos", "", "", R.drawable.gde)
            groups = groups + groupItem("Calculo III", "unicamp",
                "Em 5 minutos", "", "", R.drawable.gde)
            groups = groups + groupItem("Dummy", "unicamp",
                "Em 5 minutos", "", "", R.drawable.gde)
            groups = groups + groupItem("Dummy2", "unicamp",
                "Em 5 minutos", "", "", R.drawable.gde)

            return groups
        }
        fun getUserSugestions(userID: String): List<groupItem>{
            // TODO: change this dummy return:
            var groups : List<groupItem> = emptyList()
            groups = groups + groupItem("MC102", " unicamp",
                "Em 5 minutos", "", "", R.drawable.gde)
            groups = groups + groupItem("Estrutura de dados(MC202)", "unicamp",
                "Em 5 minutos", "", "", R.drawable.gde)
            groups = groups + groupItem("Dummy", "unicamp",
                "Em 5 minutos", "", "", R.drawable.gde)
            groups = groups + groupItem("Dummy2", "unicamp",
                "Em 5 minutos", "", "", R.drawable.gde)

            return groups
        }
    }
}