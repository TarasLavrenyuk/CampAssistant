package com.lavreniuk.campassistant.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lavreniuk.campassistant.models.User

@Database(
    entities = [User::class],
    version = 1
)
@TypeConverters(TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}