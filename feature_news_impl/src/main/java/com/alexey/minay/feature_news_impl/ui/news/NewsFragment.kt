package com.alexey.minay.feature_news_impl.ui.news

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.core_ui.withArgs
import com.alexey.minay.feature_news_impl.R
import com.alexey.minay.feature_news_impl.databinding.FragmentNewsBinding
import com.alexey.minay.feature_news_impl.di.NewsComponent
import com.alexey.minay.feature_news_impl.presentation.news.NewsViewModel

class NewsFragment : Fragment(R.layout.fragment_news) {

    private val mUrl: String get() = arguments?.getString(URL) ?: throw IllegalArgumentException()
    private val mNewsUd: String
        get() = arguments?.getString(NEWS_ID) ?: throw IllegalArgumentException()
    private val mBinding by viewBindings(FragmentNewsBinding::bind)
    private val mViewModel by viewModels<NewsViewModel> {
        NewsComponent.get().viewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
        initOnBackPressed()
    }

    private fun initWebView() = with(mBinding) {
        webView.loadUrl(mUrl)
    }

    private fun initOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            mViewModel.openNewsSummary(mNewsUd)
        }
    }

    companion object {
        private const val URL = "url"
        private const val NEWS_ID = "news_id"

        fun newInstance(url: String, newsId: String) = NewsFragment().withArgs {
            putString(URL, url)
            putString(NEWS_ID, newsId)
        }

    }

}