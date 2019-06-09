package com.example.groupfinder.Data.DB

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groupfinder.Data.Common.*

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