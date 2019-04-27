package com.example.groupfinder.base_classes

// A dummy item representing a piece of place.
data class groupItem(var groupID: Int, var mainSubject: String, var place: String, var timeToBegin: String,
                     var timeToEnd: String, var details: String, var image: Int, var contents: List<contentItem>) {
    override fun toString(): String = mainSubject
}

data class contentItem(var contentID: Int, var name: String, var link: String) {
    override fun toString(): String = name
}