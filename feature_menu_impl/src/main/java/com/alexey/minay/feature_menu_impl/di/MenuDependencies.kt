package com.alexey.minay.feature_menu_impl.di

import com.alexey.minay.core_navigation.IMenuFragmentFlowProvider
import com.alexey.minay.core_navigation.INavigator

interface MenuDependencies {
    fun provideNavigator(): INavigator
    fun provideMenuFragmentFlowProvider(): IMenuFragmentFlowProvider
}