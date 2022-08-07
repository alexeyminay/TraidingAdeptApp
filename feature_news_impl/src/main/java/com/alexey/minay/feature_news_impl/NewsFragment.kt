package com.alexey.minay.feature_news_impl

import androidx.fragment.app.Fragment

class NewsFragment : Fragment(R.layout.fragment_news) {



    companion object {
        fun newInstance() = NewsListFragment()
    }

}