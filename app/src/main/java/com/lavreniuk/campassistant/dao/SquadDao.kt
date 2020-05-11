package com.lavreniuk.campassistant.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.lavreniuk.campassistant.models.Squad
import com.lavreniuk.campassistant.models.crossrefs.SquadWithPupils

@Dao
interface SquadDao : AbstractDao<Squad> {

    /**
     * @return list of all squads with pupils in the following order: first squad is an active one, all the followings are sorted by [Squad.creationDate]
     */
    @Transaction
    @Query("SELECT * FROM squads S ORDER BY S.isCurrent DESC, S.creationDate DESC ")
    fun getSquadsWithPupils(): LiveData<List<SquadWithPupils>>

    @Query("DELETE FROM squads ")
    override fun deleteAll()
}
