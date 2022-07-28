package com.alexey.minay.feature_navigation_impl

import androidx.fragment.app.Fragment
import com.alexey.minay.core_dagger2.FeatureScope
import com.alexey.minay.core_navigation.Action
import com.alexey.minay.core_navigation.IMenuFragmentFlow
import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_utils.exhaustive
import com.alexey.minay.core_utils.modify
import com.alexey.minay.feature_menu_api.IMenuFragmentProvider
import com.alexey.minay.feature_onboarding_api.IOnBoardingFragmentProvider
import com.alexey.minay.feature_quotes_chart_api.IQuotesFragmentsProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@FeatureScope
class ScreenNavigator @Inject constructor(
    private val quotesFragmentsProvider: IQuotesFragmentsProvider,
    private val onBoardingFragmentProvider: IOnBoardingFragmentProvider,
    private val menuFragmentProvider: IMenuFragmentProvider,
    private val reducer: ScreenReducer,
    initScreen: Screen
) : INavigator, IMenuFragmentFlow {

    val mainFragmentFlow: Flow<Fragment>
        get() = mCurrentScreen.map { it.asMainFragment() }

    override val menuFragmentFlow: Flow<Fragment?>
        get() = mCurrentScreen.map { it.asMenuFragment() }

    private val mCurrentScreen = MutableStateFlow(initScreen)

    override fun perform(action: Action) {
        with(reducer) {
            mCurrentScreen.modify { reduce(action) }
        }
    }

    private fun Screen.asMainFragment(): Fragment {
        return when (this) {
            is Screen.Menu -> menuFragmentProvider.provideMenuFragment()
            Screen.OnBoarding -> onBoardingFragmentProvider.provideOnBoardingFragment()
        }.exhaustive
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