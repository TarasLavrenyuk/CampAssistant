package com.lavreniuk.campassistant.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lavreniuk.campassistant.enums.PupilParamType
import com.lavreniuk.campassistant.models.Param
import com.lavreniuk.campassistant.models.Pupil
import com.lavreniuk.campassistant.models.PupilParam
import com.lavreniuk.campassistant.models.Squad
import com.lavreniuk.campassistant.models.User
import com.lavreniuk.campassistant.models.crossrefs.SquadPupilCrossRef
import com.lavreniuk.campassistant.utils.ioThread

@Database(
    entities = [User::class, Param::class, Squad::class, Pupil::class, SquadPupilCrossRef::class, PupilParam::class],
    version = 9
)
@TypeConverters(com.lavreniuk.campassistant.utils.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun paramDao(): ParamDao
    abstract fun squadDao(): SquadDao
    abstract fun pupilDao(): PupilDao
    abstract fun pupilParamDao(): PupilParamDao

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
                            prepopulateDB(context)
                        }
                    }
                })
                .build()

        private fun prepopulateDB(context: Context) {
            with(getInstance(context)) {
                squadDao().deleteAll()
                squadPupilCrossRefDao().deleteAll()
                pupilDao().deleteAll()
                pupilParamDao().deleteAll()


                val squad = Squad(squadName = "Awesome squad", isCurrent = true)
                squadDao().insert(squad)

                val pupil1 = Pupil(firstName = "Taras", lastName = "Lavreniuk")
                val pupil2 = Pupil(firstName = "Max", lastName = "Kovalenko")
                val pupil3 = Pupil(firstName = "Den", lastName = "Petrov")
                val pupil4 = Pupil(firstName = "Kiril", lastName = "Ivanov")
                val pupil5 = Pupil(firstName = "Arthur", lastName = "Jameson")
                val pupil6 = Pupil(firstName = "Cristiano", lastName = "Ronaldo")
                val pupil7 = Pupil(firstName = "Leo")
                val pupil8 = Pupil(firstName = "Andres", lastName = "Iniesta")
                val pupil9 = Pupil(firstName = "Xavi")
                val pupil10 = Pupil(firstName = "Carles", lastName = "Puyol")

//                val pupilParam1_1 = PupilParam(
//                    pupilId = pupil1.pupilId,
//                    paramName = "Room",
//                    paramValue = "Room #1",
//                    paramType = PupilParamType.Room
//                )
                val pupilParam1_2 = PupilParam(
                    pupilId = pupil1.pupilId,
                    paramName = "Phone",
                    paramValue = "+380938213708",
                    paramType = PupilParamType.PrimaryNumber
                )
                val pupilParam1_3 = PupilParam(
                    pupilId = pupil1.pupilId,
                    paramName = "Birthday",
                    paramValue = "22/3/1997",
                    paramType = PupilParamType.BirthDay
                )
                val pupilParam1_4 = PupilParam(
                    pupilId = pupil1.pupilId,
                    paramName = "Instagram",
                    paramValue = "https://www.instagram.com/taras.lavrenyuk/",
                    paramType = PupilParamType.Social
                )
                val pupilParam2 = PupilParam(
                    pupilId = pupil2.pupilId,
                    paramName = "Room",
                    paramValue = "Room #2",
                    paramType = PupilParamType.Room
                )
                val pupilParam3 = PupilParam(
                    pupilId = pupil3.pupilId,
                    paramName = "Room",
                    paramValue = "Room #3",
                    paramType = PupilParamType.Room
                )
                val pupilParam4 = PupilParam(
                    pupilId = pupil4.pupilId,
                    paramName = "Room",
                    paramValue = "Room #4",
                    paramType = PupilParamType.Room
                )
                val pupilParam5 = PupilParam(
                    pupilId = pupil5.pupilId,
                    paramName = "Room",
                    paramValue = "Room #1",
                    paramType = PupilParamType.Room
                )
                val pupilParam6 = PupilParam(
                    pupilId = pupil6.pupilId,
                    paramName = "Room",
                    paramValue = "Room #2",
                    paramType = PupilParamType.Room
                )
                val pupilParam7 = PupilParam(
                    pupilId = pupil7.pupilId,
                    paramName = "Room",
                    paramValue = "Room #3",
                    paramType = PupilParamType.Room
                )
                val pupilParam8 = PupilParam(
                    pupilId = pupil8.pupilId,
                    paramName = "Room",
                    paramValue = "Room #4",
                    paramType = PupilParamType.Room
                )
                val pupilParam9 = PupilParam(
                    pupilId = pupil9.pupilId,
                    paramName = "Room",
                    paramValue = "Room #1",
                    paramType = PupilParamType.Room
                )
                val pupilParam10 = PupilParam(
                    pupilId = pupil10.pupilId,
                    paramName = "Room",
                    paramValue = "Room #2",
                    paramType = PupilParamType.Room
                )

                pupilParamDao().insert(
//                    pupilParam1_1,
                    pupilParam1_2,
                    pupilParam1_3,
                    pupilParam1_4,
                    pupilParam2,
                    pupilParam3,
                    pupilParam4,
                    pupilParam5,
                    pupilParam6,
                    pupilParam7,
                    pupilParam8,
                    pupilParam9,
                    pupilParam10
                )

                val pupils = listOf(
                    pupil1,
                    pupil2,
                    pupil3,
                    pupil4,
                    pupil5,
                    pupil6,
                    pupil7,
                    pupil8,
                    pupil9,
                    pupil10
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
}
