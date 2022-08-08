package com.alexey.minay.feature_news_impl.ui

import androidx.fragment.app.Fragment
import com.alexey.minay.feature_news_impl.NewsListFragment
import com.alexey.minay.feature_news_impl.R

class NewsFragment : Fragment(R.layout.fragment_news) {


    companion object {
        fun newInstance() = NewsListFragment()
    }

}