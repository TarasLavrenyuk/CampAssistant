package com.lavreniuk.campassistant.dao

import androidx.room.Insert
import androidx.room.Update

interface AbstractDao<T> {

    @Insert
    fun insert(vararg obj: T)

    @Insert
    fun insert(obj: T)

    @Update
    fun update(obj: T)

    fun deleteAll()
}
