package com.alexey.minay.feature_quotes_chart_api

import androidx.fragment.app.Fragment

interface IQuotesFragmentsProvider {
    fun provideChartFragment(): Fragment
    fun provideListFragment(): Fragment
}