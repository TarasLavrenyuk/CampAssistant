package com.lavreniuk.campassistant.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lavreniuk.campassistant.models.Person

@Database(
    entities = [Person::class],
    version = 1
)
@TypeConverters(com.lavreniuk.campassistant.utils.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): PersonDao

}