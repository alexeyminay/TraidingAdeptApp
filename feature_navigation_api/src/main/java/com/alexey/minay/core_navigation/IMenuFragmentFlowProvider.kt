package com.alexey.minay.core_navigation

import androidx.fragment.app.Fragment
import kotlinx.coroutines.flow.Flow

interface IMenuFragmentFlowProvider {
    val menuFragmentFlow: Flow<Pair<Fragment?, Extras?>>
}