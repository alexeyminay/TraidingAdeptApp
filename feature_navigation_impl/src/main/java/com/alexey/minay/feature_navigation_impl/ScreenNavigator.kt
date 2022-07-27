package com.alexey.minay.feature_navigation_impl

import androidx.fragment.app.Fragment
import com.alexey.minay.core_navigation.Action
import com.alexey.minay.core_navigation.IMenuFragmentFlow
import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_utils.exhaustive
import com.alexey.minay.core_utils.modify
import com.alexey.minay.feature_quotes_chart_api.IQuotesFragmentsProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class ScreenNavigator(
    private val quotesFragmentsProvider: IQuotesFragmentsProvider,
    private val reducer: ScreenReducer,
    coroutineScope: CoroutineScope,
    initScreen: Screen
) : INavigator, IMenuFragmentFlow {

    val mainFragmentFlow: Flow<Fragment>
        get() = mCurrentScreen.map { it.asMainFragment() }

    override val menuFragmentFlow: Flow<Fragment?>
        get() = mCurrentScreen.map { it.asMenuFragment() }

    private val mCurrentScreen = MutableStateFlow(initScreen)

    init {
        mCurrentScreen.onEach { }
            .launchIn(coroutineScope)
    }

    override fun perform(action: Action) {
        with(reducer) {
            mCurrentScreen.modify { reduce(action) }
        }
    }

    private fun Screen.asMainFragment(): Fragment {
        when (this) {
            is Screen.Menu -> TODO()
            Screen.OnBoarding -> TODO()
        }.exhaustive
        TODO()
    }

    private fun Screen.asMenuFragment(): Fragment? =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.News -> TODO()
                Screen.MenuItem.QuotesChart -> quotesFragmentsProvider.provideChartFragment()
                Screen.MenuItem.QuotesList -> quotesFragmentsProvider.provideListFragment()
            }
            else -> null
        }

}