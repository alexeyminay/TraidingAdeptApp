package com.alexey.minay.tradingadeptapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.alexey.minay.feature_menu_impl.MenuFragment

class MainActivity : AppCompatActivity() {

    private val mViewModel by viewModels<QuotesChartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mViewModel.fetch()
//        subscribeToViewModel()
        supportFragmentManager.commit {
            add(R.id.fragmentContainer, MenuFragment.newInstance(), null)
        }
    }

//    private fun subscribeToViewModel() {
//        mViewModel.state.flowWithLifecycle(lifecycle)
//            .onEach { /*findViewById<QuotesChartView>(R.id.chart).setValue(quotes = it.quotes)*/ }
//            .launchIn(lifecycleScope)
//    }
}