package com.alexey.minay.core_remote

import com.squareup.moshi.Moshi

object MoshiFactory {

    fun create() =
        Moshi.Builder()
            .build()

}