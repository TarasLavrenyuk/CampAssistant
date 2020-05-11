package com.lavreniuk.campassistant.utils

import java.util.UUID

object GeneratorUtils {

    fun generateUUID(): String {
        return "${UUID.randomUUID()}"
    }
}
