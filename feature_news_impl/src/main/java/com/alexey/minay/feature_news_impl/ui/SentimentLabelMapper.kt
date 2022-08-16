package com.alexey.minay.feature_news_impl.ui

import android.widget.ImageView
import androidx.core.view.isVisible
import com.alexey.minay.feature_news_impl.R
import com.alexey.minay.feature_news_impl.domain.SentimentLabel

object SentimentLabelMapper {

    fun map(
        sentimentLabel: SentimentLabel,
        icon: ImageView,
        subIcon: ImageView
    ) {
        when (sentimentLabel) {
            SentimentLabel.BEARISH -> {
                icon.isVisible = true
                subIcon.isVisible = true
                icon.setImageResource(R.drawable.ic_bear)
            }
            SentimentLabel.SOMEWHAT_BEARISH -> {
                icon.isVisible = true
                subIcon.isVisible = false
                icon.setImageResource(R.drawable.ic_bear)
            }
            SentimentLabel.NEUTRAL -> {
                icon.isVisible = false
                subIcon.isVisible = false
            }
            SentimentLabel.SOMEWHAT_BULLISH -> {
                icon.isVisible = true
                subIcon.isVisible = false
                icon.setImageResource(R.drawable.ic_bull)
            }
            SentimentLabel.BULLISH -> {
                icon.isVisible = true
                subIcon.isVisible = true
                icon.setImageResource(R.drawable.ic_bull)
            }
        }
    }

}