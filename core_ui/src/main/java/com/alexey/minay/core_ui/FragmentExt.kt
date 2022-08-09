package com.alexey.minay.core_ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

fun <T> Fragment.viewBindings(initializer: (View) -> T) = uiLazy {
    var value: T? = initializer(requireView())

    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            value = null
        }
    })
    value ?: throw IllegalStateException()
}

fun <T : Fragment> T.withArgs(block: Bundle.() -> Unit): T {
    arguments = Bundle().apply {
        block()
    }
    return this
}