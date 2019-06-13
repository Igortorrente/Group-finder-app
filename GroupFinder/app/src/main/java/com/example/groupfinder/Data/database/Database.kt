package com.example.groupfinder.Data.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groupfinder.Data.entities.Class
import com.example.groupfinder.Data.entities.Content
import com.example.groupfinder.Data.entities.UserData
import com.example.groupfinder.Data.entities.UserGroups

@Dao
interface UserDao{
    // Groups Queries
    @Query("SELECT * FROM Groups")
    fun getAllGroups(): LiveData<List<UserGroups>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGroup(group: UserGroups): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateGroup(group: UserGroups): Int

    @Delete
    fun deleteGroup(group: UserGroups): Int

    // Group content
    @Query("SELECT * FROM Content WHERE content_id LIKE :id")
    fun getAllGroupContent(id: Int):  LiveData<List<Content>>

    @Query("SELECT * FROM Content")
    fun getAllContents():  LiveData<List<Content>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGroupContents(content: Content): Long

    @Delete
    fun deleteGroupContents(content: Content): Int

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateGroupContents(content: Content): Int

    // Class Queries
    @Query("SELECT * FROM Class")
    fun getAllUserClasses(): LiveData<List<Class>>

    @Delete
    fun deleteUserClass(userClass: Class): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserClass(userClass: Class): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateUserClass(userClass: Class): Int

    // UserData Queries
    @Query("SELECT * FROM UserData")
    fun getUserData(): LiveData<UserData>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateUserData(UserData: UserData): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insetUserData(UserData: UserData): Long
}

@Database(entities = arrayOf(
    UserData::class, Class::class,
    UserGroups::class, Content::class), version = 1, exportSchema = false)
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