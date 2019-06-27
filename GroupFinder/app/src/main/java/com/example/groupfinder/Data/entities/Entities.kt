package com.example.groupfinder.Data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// UserRepo info
@Parcelize
@Entity(tableName = "UserData")
data class UserData(
    @PrimaryKey val ra: Int,
    @SerializedName("nome")
    val name: String = "",
    @SerializedName("curso")
    val course: String = "",
    @SerializedName("senha")
    val password:String? = ""
) : Parcelable

// UserRepo classes table
@Entity
data class Class(
    @PrimaryKey @ColumnInfo(name = "class_id") val id: Int,
    val description: String
)

@Parcelize
@Entity(tableName = "Groups")
data class UserGroups(
    @PrimaryKey @ColumnInfo(name = "group_id") val id: Int,
    @SerializedName("disciplina")
    val subject: String,
    @SerializedName("detalhes")
    val detail: String,
    @SerializedName("data_ini")
    val data_init: Int,
    @SerializedName("data_fim")
    val data_end: Int,
    // TODO: Probably T need change this to put the name of creator and a image
    @SerializedName("usuario_criador")
    val user_creator: Int,
    @SerializedName("local")
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
    var description: String,
    val url: String
) : Parcelable
