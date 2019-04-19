package com.example.groupfinder.base_classes

// A dummy item representing a piece of place.
data class groupItem(var mainSubject: String, var place: String, var timeToBegin: String,
                     var timeToEnd: String, var details: String, var image: Int) {
    override fun toString(): String = mainSubject
}
