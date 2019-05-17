package com.example.groupfinder.base_classes

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

// User info
@Entity(tableName = "User")
data class userData(
    @PrimaryKey val ra: Int,
    val name: String,
    val course: String,
    val password:String
)

// User classes table
@Entity
data class Classes(
    @PrimaryKey @ColumnInfo(name = "class_id") val id: Int,
    val description: String
)

@Entity (tableName = "Meetings")
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
)

@Entity(foreignKeys = arrayOf(ForeignKey(
            entity = UserMeetings::class,
            parentColumns = arrayOf("meet_id"),
            childColumns = arrayOf("content_id"),
            onDelete = CASCADE)))
data class Contents(
    @PrimaryKey @ColumnInfo(name = "content_id") val id: Int,
    val description: String,
    val url: String
)

@Dao
interface UserDao{
    // Meeting Queries
    @Query("SELECT * FROM Meetings")
    fun getAllMeeting(): List<UserMeetings>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMeeting(meeting: UserMeetings)

    @Update
    fun updateMeet(meeting: UserMeetings)

    @Delete
    fun deleteMeetings(meeting: List<UserMeetings>)

    // Meeting content
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMeetContents(contents: List<Contents>)

    @Delete
    fun deleteMeetContent(contents: List<Contents>)

    @Update
    fun updateMeetContent(contents: List<Contents>)

    // Class Queries
    @Query("SELECT * FROM Classes")
    fun getAllUserClasses(): List<Classes>

    @Delete
    fun deleteUserClass(userClass: Classes)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserClass(userClass: Classes)

    @Update
    fun updateUserClass(userClass: Classes)

    // User Queries
    @Query("SELECT * FROM User")
    fun getUserData(): List<userData>

    @Update
    fun updateUserData(userData: userData)

    @Insert
    fun insetUserData(userData: userData)
}

@Database(entities = arrayOf(userData::class, Classes::class,
    UserMeetings::class, Contents::class), version = 1)
abstract class userDatabase : RoomDatabase(){
}