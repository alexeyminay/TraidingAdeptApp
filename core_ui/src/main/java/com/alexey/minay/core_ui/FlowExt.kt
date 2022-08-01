package com.alexey.minay.core_ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> Flow<T>.onEachWithLifecycle(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) =
    flowWithLifecycle(lifecycleOwner.lifecycle)
        .onEach(block)
        .launchIn(lifecycleOwner.lifecycleScope)

fun Flow<Fragment?>.distinctUntilFragmentChanged() = flow {
    var previousFragment: Fragment? = null
    this@distinctUntilFragmentChanged.collect { newFragment ->
        if (newFragment?.javaClass != previousFragment?.javaClass) {
            previousFragment = newFragment
            emit(newFragment)
        }
    }
}