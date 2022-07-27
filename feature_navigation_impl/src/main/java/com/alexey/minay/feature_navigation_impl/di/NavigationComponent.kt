package com.alexey.minay.feature_navigation_impl.di

import com.alexey.minay.core_dagger2.FeatureScope
import com.alexey.minay.core_dagger2.NeedInitializeException
import com.alexey.minay.feature_navigation_impl.ScreenNavigator
import dagger.Component

@FeatureScope
@Component(
    modules = [NavigationModule::class],
    dependencies = [NavigationDependencies::class]
)
interface NavigationComponent {

    val screenNavigator: ScreenNavigator

    companion object {

        private var mNavigationComponent: NavigationComponent? = null

        fun init(dependencies: NavigationDependencies) {
            mNavigationComponent = DaggerNavigationComponent.builder()
                .navigationDependencies(dependencies)
                .build()
        }

        fun get() = mNavigationComponent ?: throw  NeedInitializeException()

    }

}