package com.lavreniuk.campassistant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.models.User.Companion.USER_ID

@Dao
interface UserDao {

    @Insert
    fun save(user: User)

    @Query("SELECT * FROM user U WHERE U.userId = :userId")
    fun getUser(userId: String = USER_ID): LiveData<User>
}
