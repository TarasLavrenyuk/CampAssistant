package com.lavreniuk.campassistant.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lavreniuk.campassistant.user.User.Companion.USER_ID

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user U WHERE U.userId = :userId")
    fun getUser(userId: String = USER_ID): LiveData<User>

    @Query("SELECT * FROM user U WHERE U.userId = :userId")
    fun getUserObject(userId: String = USER_ID): User?

    @Query("SELECT U.firstName FROM user U WHERE U.userId = :userId")
    fun getUserName(userId: String = USER_ID): LiveData<String>

    @Query("SELECT U.photo FROM user U WHERE U.userId = :userId")
    fun getUserPhoto(userId: String = USER_ID): LiveData<String>

    @Query("SELECT U.photo FROM user U WHERE U.userId = :userId")
    fun getUserPhotoObject(userId: String = USER_ID): String

    @Query("UPDATE user SET photo = :path WHERE userId = :userId")
    fun updateAvatar(userId: String = USER_ID, path: String? = null)
}
