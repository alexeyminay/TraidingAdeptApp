package com.alexey.minay.feature_quotes_chart_impl

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.alexey.minay.feature_quotes_chart_impl.di.QuotesChartComponent
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesChartViewModel
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesStore
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ChartFragment : Fragment(R.layout.fragment_chart) {

    private val mViewModel by viewModels<QuotesChartViewModel> {
        QuotesChartComponent.get().viewModelProviderFactory
    }

    @Inject
    lateinit var store: QuotesStore
    private val mComponent get() = QuotesChartComponent.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mComponent.inject(this)
        mViewModel.fetch()
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        mViewModel.state.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                requireView().findViewById<QuotesChartView>(R.id.chart).setValue(quotes = it.quotes)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    companion object {
        fun newInstance() = ChartFragment()
    }

}