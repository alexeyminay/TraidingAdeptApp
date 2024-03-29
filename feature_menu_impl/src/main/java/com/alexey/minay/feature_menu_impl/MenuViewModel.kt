package com.alexey.minay.feature_menu_impl

import com.alexey.minay.core_navigation.Action
import com.alexey.minay.core_navigation.IMenuFragmentFlowProvider
import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_ui.SingleStateViewModel
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val menuFragmentFlowProvider: IMenuFragmentFlowProvider,
    private val navigator: INavigator
): SingleStateViewModel<Unit, Nothing>(Unit) {

    val menuFragmentFlow get() = menuFragmentFlowProvider.menuFragmentFlow
    val selectedMenuItem get() = menuFragmentFlowProvider.selectedMenuItemFlow

    fun openQuotesList() {
        navigator.perform(Action.SelectQuotesListItem)
    }

    fun openQuotesChart() {
        navigator.perform(Action.SelectQuotesChartItem)
    }

    fun openNewsList() {
        navigator.perform(Action.SelectNewsListItem)
    }

}