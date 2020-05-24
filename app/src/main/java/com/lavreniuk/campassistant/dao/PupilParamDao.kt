package com.lavreniuk.campassistant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.lavreniuk.campassistant.models.PupilParam

@Dao
interface PupilParamDao : AbstractDao<PupilParam> {

    @Query(
        """SELECT PP.* FROM pupil_params PP WHERE PP.pupilId = :pupilId 
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
    fun getPupilParams(pupilId: String): LiveData<List<PupilParam>>

    @Query("DELETE FROM pupil_params ")
    override fun deleteAll()

    @Query("DELETE FROM pupil_params WHERE pupilParamId = :pupilParamId ")
    fun deleteById(pupilParamId: String)
}
