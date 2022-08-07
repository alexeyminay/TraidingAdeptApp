package com.alexey.minay.feature_news_impl.di

import com.alexey.minay.core_dagger2.FeatureScope
import com.alexey.minay.core_dagger2.NeedInitializeException
import com.alexey.minay.core_dagger2.ViewModelProviderFactory
import dagger.Component

@Component(
    dependencies = [NewsDependencies::class],
    modules = [NewsBinding::class, NewsModule::class]
)
@FeatureScope
interface NewsComponent {

    val viewModelProviderFactory: ViewModelProviderFactory

    companion object {

        private var mComponent: NewsComponent? = null

        fun init(dependencies: NewsDependencies) {
            mComponent = DaggerNewsComponent.builder()
                .newsDependencies(dependencies)
                .build()
        }

        fun get() = mComponent ?: throw NeedInitializeException()

    }

}