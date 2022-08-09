package com.alexey.minay.core_navigation

import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow

interface IMenuFragmentFlowProvider {
    val menuFragmentFlow: Flow<Pair<Fragment?, View?>>
}