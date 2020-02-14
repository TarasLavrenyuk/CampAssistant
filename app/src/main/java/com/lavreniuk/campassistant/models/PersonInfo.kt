package com.lavreniuk.campassistant.models

import com.lavreniuk.campassistant.enums.Gender
import java.util.*

open class PersonInfo(
    var firstName: String,
    var lastName: String? = null,
    var db: Date? = null,
    var photo: String? = null,
    var gender: Gender? = null
)