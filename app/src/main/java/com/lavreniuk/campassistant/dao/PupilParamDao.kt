package com.lavreniuk.campassistant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lavreniuk.campassistant.enums.PupilParamType
import com.lavreniuk.campassistant.models.PupilParam

@Dao
interface PupilParamDao : AbstractDao<PupilParam> {

    @Query(
        """SELECT PP.* FROM pupil_params PP WHERE PP.pupilId = :pupilId AND PP.paramType IN (:params)
            ORDER BY (
                CASE paramType

                WHEN 'Room'
                THEN 1

                WHEN 'PrimaryNumber'
                THEN 2

                WHEN 'BirthDay'
                THEN 3

                WHEN 'Number'
                THEN 4

                WHEN 'Social'
                THEN 5

                END
            ) ASC, createdAt DESC
        """
    )
    fun getPupilGeneralParams(
        pupilId: String,
        params: List<String> = PupilParamType.getGeneralParams().toList()
    ): LiveData<List<PupilParam>>

    @Query(
        """SELECT PP.* FROM pupil_params PP WHERE PP.pupilId = :pupilId AND PP.paramType IN (:params)
            ORDER BY (
                CASE paramType

                WHEN 'Height'
                THEN 1

                WHEN 'Weight'
                THEN 2

                WHEN 'Blood'
                THEN 3

                WHEN 'Note'
                THEN 4

                END
            ) ASC, createdAt DESC
        """
    )
    fun getPupilHealthParams(
        pupilId: String,
        params: List<String> = PupilParamType.getHealthParams().toList()
    ): LiveData<List<PupilParam>>

    @Query("DELETE FROM pupil_params ")
    override fun deleteAll()

    @Query("DELETE FROM pupil_params WHERE pupilParamId = :pupilParamId ")
    fun deleteById(pupilParamId: String)
}
