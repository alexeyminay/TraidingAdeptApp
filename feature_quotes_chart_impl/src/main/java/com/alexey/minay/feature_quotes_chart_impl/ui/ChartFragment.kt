package com.alexey.minay.feature_quotes_chart_impl.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.alexey.minay.core_ui.render
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.feature_quotes_chart_impl.R
import com.alexey.minay.feature_quotes_chart_impl.databinding.FragmentChartBinding
import com.alexey.minay.feature_quotes_chart_impl.di.QuotesChartComponent
import com.alexey.minay.feature_quotes_chart_impl.domain.Quotation
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesAction
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesStore
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.chart.QuotesChartState
import kotlinx.coroutines.flow.map
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
        initSwipeRefreshLayout()
        subscribeStore()
    }

    private fun initSwipeRefreshLayout() = with(mBinding) {
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setColorSchemeResources(com.alexey.minay.core_ui.R.color.primaryVariant)
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(com.alexey.minay.core_ui.R.color.pageBackground)
    }

    private fun subscribeStore() = with(store.state.map { it.quotesChartState }) {
        render(viewLifecycleOwner, QuotesChartState::quotation, ::renderChart)
        render(viewLifecycleOwner, QuotesChartState::type, ::renderType)
        render(viewLifecycleOwner, QuotesChartState::isRefreshing, ::renderRefreshing)
    }

    private fun renderChart(quotation: List<Quotation>) {
        mBinding.chart.setValue(quotation)
    }

    private fun renderType(type: QuotesChartState.Type) = with(mBinding) {
        when (type) {
            QuotesChartState.Type.INIT -> {
                chart.isVisible = false
                loadingGroup.root.isVisible = true
                emptyGroup.root.isVisible = false
            }
            QuotesChartState.Type.DATA -> {
                chart.isVisible = true
                loadingGroup.root.isVisible = false
                emptyGroup.root.isVisible = false
            }
            QuotesChartState.Type.ERROR -> {
                chart.isVisible = false
                loadingGroup.root.isVisible = false
                emptyGroup.root.isVisible = true
            }
        }
    }

    private fun renderRefreshing(isRefreshing: Boolean) {
        mBinding.swipeRefreshLayout.isRefreshing = isRefreshing
    }

    companion object {
        fun newInstance() = ChartFragment()
    }

}