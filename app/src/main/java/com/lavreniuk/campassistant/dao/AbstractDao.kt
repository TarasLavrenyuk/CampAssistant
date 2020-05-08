package com.lavreniuk.campassistant.dao

import androidx.room.Insert

interface AbstractDao<T> {

    @Insert
    fun insert(vararg obj: T)

    @Insert
    fun insert(obj: T)

    fun deleteAll()
}
