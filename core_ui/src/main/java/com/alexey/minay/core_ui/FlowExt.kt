package com.alexey.minay.core_ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.*

fun <T> Flow<T>.onEachWithLifecycle(lifecycleOwner: LifecycleOwner, block: suspend (T) -> Unit) =
    flowWithLifecycle(lifecycleOwner.lifecycle)
        .onEach(block)
        .launchIn(lifecycleOwner.lifecycleScope)

fun <From, To> Flow<From>.render(
    lifecycleOwner: LifecycleOwner,
    mapper: (From) -> To,
    block: suspend (To) -> Unit
) = map(mapper)
    .onEachWithLifecycle(lifecycleOwner, block)

fun Flow<Fragment?>.distinctUntilFragmentChanged() = flow {
    var previousFragment: Fragment? = null
    this@distinctUntilFragmentChanged.collect { newFragment ->
        if (newFragment?.javaClass != previousFragment?.javaClass) {
            previousFragment = newFragment
            emit(newFragment)
        }
    }
}