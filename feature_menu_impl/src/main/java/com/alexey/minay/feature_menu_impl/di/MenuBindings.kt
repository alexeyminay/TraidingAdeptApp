package com.alexey.minay.feature_menu_impl.di

import androidx.lifecycle.ViewModel
import com.alexey.minay.core_dagger2.ViewModelKey
import com.alexey.minay.feature_menu_impl.MenuViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MenuBindings {

    @Binds
    @[IntoMap ViewModelKey(MenuViewModel::class)]
    fun bindMenuViewModel(menuViewModel: MenuViewModel): ViewModel

}