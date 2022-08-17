package com.alexey.minay.feature_quotes_chart_impl.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.feature_quotes_chart_impl.R
import com.alexey.minay.feature_quotes_chart_impl.databinding.FragmentChartBinding
import com.alexey.minay.feature_quotes_chart_impl.di.QuotesChartComponent
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesAction
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesChartViewModel
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesStore
import javax.inject.Inject

class ChartFragment : Fragment(R.layout.fragment_chart) {

    @Inject
    lateinit var store: QuotesStore
    private val mComponent get() = QuotesChartComponent.get()
    private val mBinding by viewBindings(FragmentChartBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mComponent.inject(this)
        store.accept(QuotesAction.FetchQuotes)
        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        store.state.onEachWithLifecycle(viewLifecycleOwner) {
            mBinding.chart.setValue(it.quotesChartState.quotation)
        }
    }

    companion object {
        fun newInstance() = ChartFragment()
    }

}