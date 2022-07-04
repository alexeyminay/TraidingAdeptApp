package com.alexey.minay.tradingadeptapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private val mViewModel by viewModels<QuotesChartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel.fetch()
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        mViewModel.state.flowWithLifecycle(lifecycle)
            .onEach { findViewById<QuotesChartView>(R.id.chart).setValue(quotes = it.quotes) }
            .launchIn(lifecycleScope)
    }
}