package com.example.groupfinder.base_classes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

// UserRepo info
@Entity(tableName = "UserData")
data class userData(
    @PrimaryKey val ra: Int,
    val name: String,
    val course: String,
    val password:String
)

// UserRepo classes table
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
    fun getAllMeeting(): LiveData<MutableList<UserMeetings>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertMeeting(meeting: UserMeetings): Long

    @Delete
    fun deleteMeetings(meeting: UserMeetings): Int

    // Meeting content
    @Query("SELECT * FROM Contents WHERE content_id LIKE :id")
    fun getAllMeetingContent(id: Int):  LiveData<MutableList<Contents>>

    @Query("SELECT * FROM Contents")
    fun getAllContents():  LiveData<MutableList<Contents>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertMeetContent(content: Contents): Long

    @Delete
    fun deleteMeetContents(content: Contents): Int

    // Class Queries
    @Query("SELECT * FROM Classes")
    fun getAllUserClasses(): LiveData<MutableList<Classes>>

    @Delete
    fun deleteUserClass(userClass: Classes): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertUserClass(userClass: Classes): Long

    // UserData Queries
    @Query("SELECT * FROM UserData")
    fun getUserData(): LiveData<userData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertUserData(userData: userData): Long
}

@Database(entities = arrayOf(userData::class, Classes::class,
    UserMeetings::class, Contents::class), version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
    abstract fun userDataDao(): UserDao
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                TODO("Rename databse")
                val instance = Room.databaseBuilder(context.applicationContext,
                    UserDatabase::class.java,"rename-me.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}