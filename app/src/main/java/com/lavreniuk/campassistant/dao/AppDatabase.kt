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
    version = 7
)
@TypeConverters(com.lavreniuk.campassistant.utils.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun paramDao(): ParamDao
    abstract fun squadDao(): SquadDao
    abstract fun pupilDao(): PupilDao

    abstract fun squadPupilCrossRefDao(): SquadPupilCrossRefDao

    companion object {
        @Volatile
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
                .addCallback(object : Callback() {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        // moving to a new thread
                        ioThread {
                            with(getInstance(context)) {
                                squadDao().deleteAll()
                                pupilDao().deleteAll()
                                squadPupilCrossRefDao().deleteAll()

                                val squad1 =
                                    Squad(squadName = "Clever forever", isCurrent = true)
                                val squad2 =
                                    Squad(squadName = "Breaking bad", isCurrent = false)
                                squadDao().insert(squad1, squad2)

                                val pupil1_1 = Pupil(firstName = "Taras")
                                val pupil1_2 = Pupil(firstName = "Dima")

                                val pupil2_1 = Pupil(firstName = "Ivan")
                                val pupil2_2 = Pupil(firstName = "Vadim")
                                val pupil2_3 = Pupil(firstName = "Alex")
                                val pupil2_4 = Pupil(firstName = "Oleg")
                                val pupil2_5 = Pupil(firstName = "Oleg")
                                val pupil2_6 = Pupil(firstName = "Oleg")
                                pupilDao().insert(
                                    pupil1_1, pupil1_2,
                                    pupil2_1, pupil2_2, pupil2_3, pupil2_4, pupil2_5, pupil2_6
                                )

                                squadPupilCrossRefDao().insert(
                                    SquadPupilCrossRef(squad1.squadId, pupil1_1.pupilId),
                                    SquadPupilCrossRef(squad1.squadId, pupil1_2.pupilId),

                                    SquadPupilCrossRef(squad2.squadId, pupil2_1.pupilId),
                                    SquadPupilCrossRef(squad2.squadId, pupil2_2.pupilId),
                                    SquadPupilCrossRef(squad2.squadId, pupil2_3.pupilId),
                                    SquadPupilCrossRef(squad2.squadId, pupil2_4.pupilId),
                                    SquadPupilCrossRef(squad2.squadId, pupil2_5.pupilId),
                                    SquadPupilCrossRef(squad2.squadId, pupil2_6.pupilId)
                                )
                            }
                        }
                    }
                }).build()
    }
}
