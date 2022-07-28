package com.alexey.minay.feature_onboarding_impl

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexey.minay.feature_onboarding_impl.di.OnBoardingComponentHolder

class OnBoardingFragment : Fragment(R.layout.fragmetn_onboarding) {

    private val mComponentHolder by viewModels<OnBoardingComponentHolder>()
    private val mViewModel by viewModels<OnBoardingViewModel> {
        mComponentHolder.component.viewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.openMenu).setOnClickListener {
            mViewModel.openMenu()
        }
    }

    companion object {
        fun newInstance() = OnBoardingFragment()
    }

}