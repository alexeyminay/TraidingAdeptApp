package com.alexey.minay.feature_navigation_impl

import android.view.View
import androidx.fragment.app.Fragment
import com.alexey.minay.core_dagger2.FeatureScope
import com.alexey.minay.core_navigation.Action
import com.alexey.minay.core_navigation.IMenuFragmentFlowProvider
import com.alexey.minay.core_navigation.INavigator
import com.alexey.minay.core_utils.exhaustive
import com.alexey.minay.core_utils.modify
import com.alexey.minay.feature_menu_api.IMenuFragmentProvider
import com.alexey.minay.feature_news_api.INewsFragmentProvider
import com.alexey.minay.feature_onboarding_api.IOnBoardingFragmentProvider
import com.alexey.minay.feature_quotes_chart_api.IQuotesFragmentsProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@FeatureScope
class ScreenNavigator @Inject constructor(
    private val newsFragmentProvider: INewsFragmentProvider,
    private val quotesFragmentsProvider: IQuotesFragmentsProvider,
    private val onBoardingFragmentProvider: IOnBoardingFragmentProvider,
    private val menuFragmentProvider: IMenuFragmentProvider,
    private val reducer: ScreenReducer,
    initScreen: Screen
) : INavigator, IMenuFragmentFlowProvider {

    val mainFragmentFlow: Flow<Fragment>
        get() = mCurrentScreen.map { it.asMainFragment() }

    override val menuFragmentFlow: Flow<Pair<Fragment?, View?>>
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

    private fun Screen.asMenuFragment(): Pair<Fragment?, View?> =
        when (this) {
            is Screen.Menu -> when (item) {
                Screen.MenuItem.NewsList -> Pair(newsFragmentProvider.provideNewsFragment(), null)
                Screen.MenuItem.QuotesChart -> Pair(quotesFragmentsProvider.provideChartFragment(), null)
                Screen.MenuItem.QuotesList -> Pair(quotesFragmentsProvider.provideQuotesListFragment(), null)
                is Screen.MenuItem.NewsSummary ->
                    Pair(newsFragmentProvider.provideNewsSummary(item.newsId), item.sharedView)
            }
            else -> Pair(null, null)
        }

}