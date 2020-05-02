package com.lavreniuk.campassistant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.models.User.Companion.USER_ID

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM user U WHERE U.userId = :userId")
    fun getUser(userId: String = USER_ID): LiveData<User>

    @Query("SELECT * FROM user U WHERE U.userId = :userId")
    fun getUserObject(userId: String = USER_ID): User?

    @Query("SELECT U.firstName FROM user U WHERE U.userId = :userId")
    fun getUserName(userId: String = USER_ID): LiveData<String>
}
