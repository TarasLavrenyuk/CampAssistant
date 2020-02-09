package com.lavreniuk.campassistant.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.utils.Converters

@Database(
    entities = [User::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}