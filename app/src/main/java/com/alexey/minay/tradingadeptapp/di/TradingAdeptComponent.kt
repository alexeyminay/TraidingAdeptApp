package com.alexey.minay.tradingadeptapp.di

import com.alexey.minay.core_dagger2.NeedInitializeException
import com.alexey.minay.core_dagger2.ViewModelProviderFactory
import com.alexey.minay.core_remote.BasicApi
import com.alexey.minay.core_utils.RequestWrapper
import com.alexey.minay.feature_menu_impl.MenuFragmentProvider
import com.alexey.minay.feature_menu_impl.di.MenuComponent
import com.alexey.minay.feature_menu_impl.di.MenuDependencies
import com.alexey.minay.feature_navigation_impl.di.NavigationComponent
import com.alexey.minay.feature_navigation_impl.di.NavigationDependencies
import com.alexey.minay.feature_news_impl.NewsFragmentProvider
import com.alexey.minay.feature_news_impl.di.NewsComponent
import com.alexey.minay.feature_news_impl.di.NewsDependencies
import com.alexey.minay.feature_onboarding_impl.OnBoardingFragmentProvider
import com.alexey.minay.feature_onboarding_impl.di.OnBoardingComponent
import com.alexey.minay.feature_onboarding_impl.di.OnBoardingDependencies
import com.alexey.minay.feature_quotes_chart_impl.di.QuotesChartComponent
import com.alexey.minay.feature_quotes_chart_impl.di.QuotesChartDependencies
import com.alexey.minay.feature_quotes_chart_impl.navigation.QuotesFragmentsProvider
import dagger.Component

@Component(
    modules = [
        TradingAdeptViewModelBinding::class,
        TradingAdeptModule::class
    ],
    dependencies = [TradingAdeptDependencies::class]
)
@AppScope
interface TradingAdeptComponent {

    val viewModelProviderFactory: ViewModelProviderFactory
    val basicApi: BasicApi

    companion object {

        private var mComponent: TradingAdeptComponent? = null

        fun get() = mComponent ?: throw NeedInitializeException()

        fun init() {
            NavigationComponent.init(
                dependencies = object : NavigationDependencies {
                    override fun provideQuotesFragmentProvider() =
                        QuotesFragmentsProvider()

                    override fun provideOnBoardingFragmentProvider() =
                        OnBoardingFragmentProvider()

                    override fun provideMenuFragmentProvider() =
                        MenuFragmentProvider()

                    override fun provideNewsFragmentProvider() =
                        NewsFragmentProvider()
                }
            )

            OnBoardingComponent.init(
                dependencies = object : OnBoardingDependencies {
                    override fun provideNavigator() =
                        NavigationComponent.get().screenNavigator
                }
            )

            MenuComponent.init(
                dependencies = object : MenuDependencies {
                    override fun provideNavigator() =
                        NavigationComponent.get().screenNavigator

                    override fun provideMenuFragmentFlowProvider() =
                        NavigationComponent.get().screenNavigator

                }
            )

            QuotesChartComponent.init(
                dependencies = object : QuotesChartDependencies {
                    override fun provideBasicApi() = get().basicApi
                    override fun provideRequestWrapper() = RequestWrapper()
                    override fun provideNavigator() = NavigationComponent.get().screenNavigator
                }
            )

            NewsComponent.init(
                dependencies = object : NewsDependencies {
                    override fun provideBasicApi() = get().basicApi
                    override fun provideRequestWrapper() = RequestWrapper()
                    override fun provideNavigator() = NavigationComponent.get().screenNavigator
                }
            )

            val dependencies = object : TradingAdeptDependencies {
                override fun provideNavigationScreen() = NavigationComponent.get().screenNavigator
            }

            mComponent = DaggerTradingAdeptComponent.builder()
                .tradingAdeptDependencies(dependencies)
                .build()
        }

    }

}