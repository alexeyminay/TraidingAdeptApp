package com.alexey.minay.tradingadeptapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.alexey.minay.core_ui.distinctUntilFragmentChanged
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.tradingadeptapp.di.TradingAdeptComponent

class TradingAdeptActivity : AppCompatActivity() {

    private val mViewModel by viewModels<TradingAdeptViewModel> {
        TradingAdeptComponent.get().viewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        mViewModel.screenFlow
            .distinctUntilFragmentChanged()
            .onEachWithLifecycle(this, ::navigateTo)
    }

    private fun navigateTo(fragment: Fragment?) {
        if (fragment == null) {
            finish()
            return
        }

        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, fragment, null)
        }
    }

    override fun onBackPressed() {
        if(onBackPressedDispatcher.hasEnabledCallbacks()) {
            onBackPressedDispatcher.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

}