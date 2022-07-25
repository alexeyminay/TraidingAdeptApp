package com.alexey.minay.core_utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class DispatchersProvider(
    val io: CoroutineContext,
    val default: CoroutineContext,
    val main: CoroutineContext
) {
    companion object {
        fun default() = DispatchersProvider(
            io = Dispatchers.IO,
            default = Dispatchers.Default,
            main = Dispatchers.Main
        )
    }
}