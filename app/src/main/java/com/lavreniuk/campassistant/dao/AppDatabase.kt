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

                                val squad = Squad(squadName = "Awesome squad")
                                squadDao().insert(squad)

                                val pupil1 = Pupil(firstName = "Taras", room = "Room #3")
                                val pupil2 = Pupil(firstName = "Max", room = "Room #3")
                                val pupil3 = Pupil(firstName = "Den", room = "Room #3")
                                val pupil4 = Pupil(firstName = "Kiril", room = "Room #3")

                                squadPupilCrossRefDao().insert(
                                    SquadPupilCrossRef(
                                        squadId = squad.squadId,
                                        pupilId = pupil1.pupilId
                                    ),
                                    SquadPupilCrossRef(
                                        squadId = squad.squadId,
                                        pupilId = pupil2.pupilId
                                    ),
                                    SquadPupilCrossRef(
                                        squadId = squad.squadId,
                                        pupilId = pupil3.pupilId
                                    ),
                                    SquadPupilCrossRef(
                                        squadId = squad.squadId,
                                        pupilId = pupil4.pupilId
                                    )
                                )

                                pupilDao().insert(pupil1, pupil2, pupil3, pupil4)
                            }
                        }
                    }
                })
                .build()
    }
}
