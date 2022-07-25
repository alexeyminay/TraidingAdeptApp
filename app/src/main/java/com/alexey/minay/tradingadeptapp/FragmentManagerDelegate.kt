package com.alexey.minay.tradingadeptapp

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

class FragmentManagerDelegate(
    private val fragmentManager: FragmentManager,
    @IdRes private val fragmentContainer: Int
) {

    fun add(fragment: Fragment) {
        fragmentManager.commit {
            add(fragmentContainer, fragment, fragment.hashCode().toString())
        }
    }

    fun replace(fragment: Fragment) {
        fragmentManager.commit {
            replace(fragmentContainer, fragment, fragment.hashCode().toString())
        }
    }

    fun pop() {
        fragmentManager.popBackStack()
    }

}