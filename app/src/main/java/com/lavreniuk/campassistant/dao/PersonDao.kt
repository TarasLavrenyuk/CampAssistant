package com.lavreniuk.campassistant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lavreniuk.campassistant.models.Person

@Dao
interface PersonDao {

    @Insert(onConflict = REPLACE)
    fun save(person: Person)

    @Query("SELECT * FROM persons WHERE personId = :personId")
    fun load(personId: String): LiveData<Person>

}
