package com.alexey.minay.feature_news_impl

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.core_ui.uiLazy
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.feature_news_impl.databinding.FragmentNewsListBinding
import com.alexey.minay.feature_news_impl.di.NewsComponent
import com.alexey.minay.feature_news_impl.presentation.list.NewsListState
import com.alexey.minay.feature_news_impl.presentation.list.NewsListViewModel
import com.alexey.minay.feature_news_impl.ui.NewsListAdapter

class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private val mBinding by viewBindings(FragmentNewsListBinding::bind)
    private val mAdapter by uiLazy { NewsListAdapter() }
    private val mViewModel by viewModels<NewsListViewModel> {
        NewsComponent.get().viewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        subscribeToViewModel()
    }

    private fun initList() {
        mBinding.quotes.adapter = mAdapter
    }

    private fun subscribeToViewModel() {
        mViewModel.state.onEachWithLifecycle(viewLifecycleOwner, ::render)
    }

    private fun render(state: NewsListState) {
        mAdapter.submitList(state.items)
    }

    companion object {
        fun newInstance() = NewsListFragment()
    }

}