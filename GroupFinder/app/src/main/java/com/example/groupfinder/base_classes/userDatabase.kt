package com.example.groupfinder.base_classes

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import kotlinx.coroutines.CoroutineScope

// UserRepo info
@Entity(tableName = "UserData")
data class UserData(
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
data class UserGroups(
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

@Entity(foreignKeys = arrayOf(ForeignKey(entity = UserGroups::class, parentColumns = arrayOf("meet_id"),
            childColumns = arrayOf("content_id"), onDelete = CASCADE)))
data class Contents(
    @PrimaryKey @ColumnInfo(name = "content_id") val id: Int,
    val description: String,
    val url: String
)

@Dao
interface UserDao{
    // Meeting Queries
    @Query("SELECT * FROM Meetings")
    fun getAllMeeting(): LiveData<List<UserGroups>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeeting(meeting: UserGroups): Long

    @Delete
    suspend fun deleteMeetings(meeting: UserGroups): Int

    // Meeting content
    @Query("SELECT * FROM Contents WHERE content_id LIKE :id")
    fun getAllMeetingContent(id: Int): LiveData<List<Contents>>

    @Query("SELECT * FROM Contents")
    fun getAllContents(): LiveData<List<Contents>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeetContent(content: Contents): Long

    @Delete
    suspend fun deleteMeetContents(content: Contents): Int

    // Class Queries
    @Query("SELECT * FROM Classes")
    fun getAllUserClasses(): LiveData<List<Classes>>

    @Delete
    suspend fun deleteUserClass(userClass: Classes): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserClass(userClass: Classes): Long

    // UserData Queries
    @Query("SELECT * FROM UserData")
    fun getUserData(): LiveData<UserData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserData(UserData: UserData): Long
}

@Database(entities = arrayOf(UserData::class, Classes::class, UserGroups::class, Contents::class), version = 1)
abstract class UserDatabase : RoomDatabase(){

    abstract fun userDataDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): UserDatabase {
            return INSTANCE ?:synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    UserDatabase::class.java,"rename-me").fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}