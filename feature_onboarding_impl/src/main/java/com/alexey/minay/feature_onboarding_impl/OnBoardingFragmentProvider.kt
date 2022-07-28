package com.alexey.minay.feature_onboarding_impl

import androidx.fragment.app.Fragment
import com.alexey.minay.feature_onboarding_api.IOnBoardingFragmentProvider

class OnBoardingFragmentProvider : IOnBoardingFragmentProvider {
    override fun provideOnBoardingFragment(): Fragment = OnBoardingFragment.newInstance()
}