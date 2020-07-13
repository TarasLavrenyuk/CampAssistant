package com.lavreniuk.campassistant.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ParamDao {

    @Insert
    fun insert(param: Param)

    @Query("SELECT * FROM params P WHERE P.ownerId = :ownerId ")
    fun getParamsByOwnerId(ownerId: String): LiveData<List<Param>>
}
