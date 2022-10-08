package com.alexey.minay.feature_quotes_chart_impl.navigation

import androidx.fragment.app.Fragment
import com.alexey.minay.feature_quotes_chart_api.IQuotesFragmentsProvider
import com.alexey.minay.feature_quotes_chart_impl.ui.chart.ChartFragment
import com.alexey.minay.feature_quotes_chart_impl.ui.list.QuotesListFragment

class QuotesFragmentsProvider : IQuotesFragmentsProvider {

    override fun provideChartFragment(): Fragment {
        return ChartFragment.newInstance()
    }

    override fun provideQuotesListFragment(): Fragment {
        return QuotesListFragment.newInstance()
    }

}