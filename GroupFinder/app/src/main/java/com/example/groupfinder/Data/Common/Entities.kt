package com.example.groupfinder.Data.Common

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
data class Classes(
    @PrimaryKey @ColumnInfo(name = "class_id") val id: Int,
    val description: String
) : Parcelable

@Parcelize
@Entity(tableName = "Meetings")
data class UserMeetings(
    @PrimaryKey @ColumnInfo(name = "meet_id") val id: Int,
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
        entity = UserMeetings::class,
        parentColumns = arrayOf("meet_id"),
        childColumns = arrayOf("content_id"),
        onDelete = ForeignKey.CASCADE
    )
))
data class Contents(
    @PrimaryKey @ColumnInfo(name = "content_id") val id: Int,
    val description: String,
    val url: String
) : Parcelable
