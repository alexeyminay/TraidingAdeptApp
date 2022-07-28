package com.alexey.minay.feature_navigation_impl.di

import com.alexey.minay.core_dagger2.FeatureScope
import com.alexey.minay.feature_navigation_impl.Screen
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

@Module
class NavigationModule {

    @Provides
    @FeatureScope
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Job())

    @Provides
    fun provideInitScreen(): Screen = Screen.OnBoarding

}