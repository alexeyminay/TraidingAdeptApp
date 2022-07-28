package com.alexey.minay.feature_menu_impl

import com.alexey.minay.feature_menu_api.IMenuFragmentProvider

class MenuFragmentProvider : IMenuFragmentProvider {
    override fun provideMenuFragment() = MenuFragment.newInstance()
}