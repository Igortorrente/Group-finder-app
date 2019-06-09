package com.example.groupfinder.base_classes

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
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
) : Parcelable

@Parcelize
@Entity(foreignKeys = arrayOf(ForeignKey(
    entity = UserMeetings::class,
    parentColumns = arrayOf("meet_id"),
    childColumns = arrayOf("content_id"),
    onDelete = CASCADE)))
data class Contents(
    @PrimaryKey @ColumnInfo(name = "content_id") val id: Int,
    val description: String,
    val url: String
) : Parcelable

@Dao
interface UserDao{
    // Meeting Queries
    @Query("SELECT * FROM Meetings")
    fun getAllMeeting(): LiveData<List<UserMeetings>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMeeting(meeting: UserMeetings): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateMeet(meeting: UserMeetings): Int

    @Delete
    fun deleteMeetings(meeting: UserMeetings): Int

    // Meeting content
    @Query("SELECT * FROM Contents WHERE content_id LIKE :id")
    fun getAllMeetingContent(id: Int):  LiveData<List<Contents>>

    @Query("SELECT * FROM Contents")
    fun getAllContents():  LiveData<List<Contents>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMeetContents(content: Contents): Long

    @Delete
    fun deleteMeetContents(content: Contents): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateMeetContents(content: Contents): Int

    // Class Queries
    @Query("SELECT * FROM Classes")
    fun getAllUserClasses(): LiveData<List<Classes>>

    @Delete
    fun deleteUserClass(userClass: Classes): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserClass(userClass: Classes): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateUserClass(userClass: Classes): Int

    // UserData Queries
    @Query("SELECT * FROM UserData")
    fun getUserData(): LiveData<UserData>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateUserData(UserData: UserData): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insetUserData(UserData: UserData): Long
}

@Database(entities = arrayOf(UserData::class, Classes::class,
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
                val instance = Room.databaseBuilder(context.applicationContext,
                    UserDatabase::class.java,"rename-me.db").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}