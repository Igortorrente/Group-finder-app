package com.example.groupfinder.base_classes


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
}