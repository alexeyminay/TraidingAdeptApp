package com.alexey.minay.core_navigation

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow

interface IMenuFragmentFlow {
    val menuFragmentFlow: Flow<Fragment?>
}