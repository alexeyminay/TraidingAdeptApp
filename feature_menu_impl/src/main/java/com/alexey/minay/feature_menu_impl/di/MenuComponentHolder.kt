package com.alexey.minay.feature_menu_impl.di

import androidx.lifecycle.ViewModel

class MenuComponentHolder : ViewModel() {

    val component = MenuComponent.create()

}