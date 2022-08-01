package com.alexey.minay.feature_quotes_chart_impl.di

import com.alexey.minay.core_dagger2.FeatureScope
import com.alexey.minay.core_dagger2.NeedInitializeException
import com.alexey.minay.core_dagger2.ViewModelProviderFactory
import dagger.Component

@Component(
    dependencies = [QuotesChartDependencies::class],
    modules = [QuotesChartBinding::class]
)
@FeatureScope
interface QuotesChartComponent {

    val viewModelProviderFactory: ViewModelProviderFactory

    companion object {

        private var mComponent: QuotesChartComponent? = null

        fun init(dependencies: QuotesChartDependencies) {
            mComponent = DaggerQuotesChartComponent.builder()
                .quotesChartDependencies(dependencies)
                .build()
        }

        fun get() = mComponent ?: throw NeedInitializeException()

    }

}