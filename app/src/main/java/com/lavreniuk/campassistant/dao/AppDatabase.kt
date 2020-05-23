package com.lavreniuk.campassistant.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lavreniuk.campassistant.models.Param
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.models.Squad
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.models.crossrefs.SquadPupilCrossRef
import com.lavreniuk.campassistant.utils.ioThread

@Database(
    entities = [User::class, Param::class, Squad::class, Pupil::class, SquadPupilCrossRef::class],
    version = 8
)
@TypeConverters(com.lavreniuk.campassistant.utils.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun paramDao(): ParamDao
    abstract fun squadDao(): SquadDao
    abstract fun pupilDao(): PupilDao

    abstract fun squadPupilCrossRefDao(): SquadPupilCrossRefDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                buildDB(context).also { INSTANCE = it }
            }

        private fun buildDB(context: Context): AppDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "campassistant_database"
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        ioThread {
                            with(getInstance(context)) {
                                squadDao().deleteAll()

                                val squad = Squad(squadName = "Awesome squad", isCurrent = true)
                                squadDao().insert(squad)

                                val pupils = listOf(
                                    Pupil(
                                        firstName = "Taras",
                                        lastName = "Lavreniuk",
                                        room = "Room #1"
                                    ),
                                    Pupil(
                                        firstName = "Max",
                                        lastName = "Kovalenko",
                                        room = "Room #2"
                                    ),
                                    Pupil(firstName = "Den", lastName = "Petrov", room = "Room #3"),
                                    Pupil(
                                        firstName = "Kiril",
                                        lastName = "Ivanov",
                                        room = "Room #4"
                                    ),
                                    Pupil(
                                        firstName = "Arthur",
                                        lastName = "Jameson",
                                        room = "Room #1"
                                    ),
                                    Pupil(
                                        firstName = "Cristiano",
                                        lastName = "Ronaldo",
                                        room = "Room #1"
                                    ),
                                    Pupil(
                                        firstName = "Leo",
                                        lastName = "Messo",
                                        room = "Room #2"
                                    ),
                                    Pupil(
                                        firstName = "Andres",
                                        lastName = "Iniesta",
                                        room = "Room #3"
                                    ),
                                    Pupil(
                                        firstName = "Xavi",
                                        lastName = "Ernandes",
                                        room = "Room #3"
                                    ),
                                    Pupil(
                                        firstName = "Carles",
                                        lastName = "Puyol",
                                        room = "Room #4"
                                    )
                                )

                                pupils.forEach {
                                    squadPupilCrossRefDao().insert(
                                        SquadPupilCrossRef(
                                            squadId = squad.squadId,
                                            pupilId = it.pupilId
                                        )
                                    )
                                }

                                pupils.forEach { pupilDao().insert(it) }
                            }
                        }
                    }
                })
                .build()
    }
}
