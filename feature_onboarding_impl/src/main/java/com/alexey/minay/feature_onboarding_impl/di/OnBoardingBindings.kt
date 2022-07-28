package com.alexey.minay.feature_onboarding_impl.di

import androidx.lifecycle.ViewModel
import com.alexey.minay.core_dagger2.ViewModelKey
import com.alexey.minay.feature_onboarding_impl.OnBoardingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface OnBoardingBindings {

    @Binds
    @[IntoMap ViewModelKey(OnBoardingViewModel::class)]
    fun bindOnBoardingViewModel(viewModel: OnBoardingViewModel): ViewModel

}