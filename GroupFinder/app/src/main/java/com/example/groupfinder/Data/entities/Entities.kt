package com.example.groupfinder.Data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// UserRepo info
@Parcelize
@Entity(tableName = "UserData")
data class UserData(
    @PrimaryKey val ra: Int,
    val name: String,
    val course: String,
    val password:String
) : Parcelable

// UserRepo classes table
@Parcelize
@Entity
data class Class(
    @PrimaryKey @ColumnInfo(name = "class_id") val id: Int,
    val description: String
) : Parcelable

@Parcelize
@Entity(tableName = "Groups")
data class UserGroups(
    @PrimaryKey @ColumnInfo(name = "group_id") val id: Int,
    val subject: String,
    val detail: String,
    val data_init: Int,
    val data_end: Int,
    val location_id: Int,
    // TODO: Probably T need change this to put the name of creator and a image
    val user_creator: Int,
    val location_description: String
) : Parcelable

@Parcelize
@Entity(foreignKeys = arrayOf(
    ForeignKey(
        entity = UserGroups::class,
        parentColumns = arrayOf("group_id"),
        childColumns = arrayOf("content_id"),
        onDelete = ForeignKey.CASCADE
    )
))
data class Content(
    @PrimaryKey @ColumnInfo(name = "content_id") val id: Int,
    val description: String,
    val url: String
) : Parcelable
