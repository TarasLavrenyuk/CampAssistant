package com.lavreniuk.campassistant.repositories

import com.lavreniuk.campassistant.dao.PupilDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PupilRepo @Inject constructor(
    private val pupilDao: PupilDao
) {
}