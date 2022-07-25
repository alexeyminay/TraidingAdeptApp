package com.alexey.minay.feature_quotes_chart_impl.navigation

import androidx.fragment.app.Fragment
import com.alexey.minay.feature_quotes_chart_api.IQuotesFragmentsProvider
import com.alexey.minay.feature_quotes_chart_impl.ChartFragment
import com.alexey.minay.feature_quotes_chart_impl.QuotesListFragment

class QuotesFragmentsProvider : IQuotesFragmentsProvider {

    override fun provideChartFragment(): Fragment {
        return ChartFragment.newInstance()
    }

    override fun provideListFragment(): Fragment {
        return QuotesListFragment.newInstance()
    }

}