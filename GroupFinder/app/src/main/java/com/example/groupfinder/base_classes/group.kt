package com.example.groupfinder.base_classes

import com.example.groupfinder.R
import java.util.*

/**
 * Helper class for providing sample place for User interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object group {


    // An array of sample (dummy) items.
    val ITEMS: MutableList<groupItem> = ArrayList()

    //A map of sample (dummy) items, by ID.
    val ITEM_MAP: MutableMap<String, groupItem> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: groupItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.mainSubject, item)
    }

    private fun createDummyItem(position: Int): groupItem {
        return groupItem(position.toString(), "MC855","12:00",
            "oi","oi", R.drawable.gde)
    }

    // A dummy item representing a piece of place.
    data class groupItem(var mainSubject: String, var place: String, var timeToBegin: String,
                         var timeToEnd: String, var details: String, var image: Int) {
        override fun toString(): String = mainSubject
    }
}
