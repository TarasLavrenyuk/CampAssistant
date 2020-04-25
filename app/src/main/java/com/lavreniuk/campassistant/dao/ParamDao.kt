package com.lavreniuk.campassistant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lavreniuk.campassistant.models.Param

@Dao
interface ParamDao {

    @Insert
    fun insert(param: Param)

    @Query("SELECT * FROM params P WHERE P.ownerId = :ownerId ")
    fun getParamsByOwnerId(ownerId: String): LiveData<List<Param>>
}
