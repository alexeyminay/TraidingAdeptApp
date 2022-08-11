package com.alexey.minay.feature_news_impl.presentation.list

import com.alexey.minay.core_dagger2.FeatureScope
import javax.inject.Inject

@FeatureScope
class NewsListStateHolder @Inject constructor(
    var state: NewsListState
)