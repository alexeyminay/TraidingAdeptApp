package com.alexey.minay.tradingadeptapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.core_utils.uiLazy
import com.alexey.minay.tradingadeptapp.di.TradingAdeptComponent

class TradingAdeptActivity : AppCompatActivity() {

    private val mViewModel by uiLazy {
        TradingAdeptComponent.get().viewModelProviderFactory.create(TradingAdeptViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun subscribeToViewModel() {
        mViewModel.screenFlow.onEachWithLifecycle(this, ::navigateTo)
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragmentContainer, fragment, null)
        }
    }

}