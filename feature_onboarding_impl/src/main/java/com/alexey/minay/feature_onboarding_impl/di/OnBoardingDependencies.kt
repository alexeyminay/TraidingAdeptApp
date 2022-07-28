package com.alexey.minay.feature_onboarding_impl.di

import com.alexey.minay.core_navigation.INavigator

interface OnBoardingDependencies {
    fun provideNavigator(): INavigator
}