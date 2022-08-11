package com.alexey.minay.feature_news_impl.ui.list

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.alexey.minay.core_navigation.Extras
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.core_ui.uiLazy
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.feature_news_impl.R
import com.alexey.minay.feature_news_impl.databinding.FragmentNewsListBinding
import com.alexey.minay.feature_news_impl.di.NewsComponent
import com.alexey.minay.feature_news_impl.domain.NewsId
import com.alexey.minay.feature_news_impl.presentation.list.NewsListState
import com.alexey.minay.feature_news_impl.presentation.list.NewsListViewModel
import com.google.android.material.transition.MaterialContainerTransform
import com.alexey.minay.core_ui.R as CoreUiR

class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private val mBinding by viewBindings(FragmentNewsListBinding::bind)
    private val mAdapter by uiLazy {
        NewsListAdapter(::openNewsSummary)
    }
    private val mViewModel by viewModels<NewsListViewModel> {
        NewsComponent.get().viewModelProviderFactory
    }

    private fun openNewsSummary(id: NewsId, view: View) {
        val scrollState = mBinding.newsList.layoutManager?.onSaveInstanceState()
        mViewModel.openNewsSummary(id, Extras(view), scrollState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = buildContainerTransform(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        initSwipeRefreshLayout()
        initList(savedInstanceState == null)
        subscribeToViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mViewModel.saveScrollState(mBinding.newsList.layoutManager?.onSaveInstanceState())
    }

    private fun initSwipeRefreshLayout() = with(mBinding) {
        swipeRefreshLayout.setColorSchemeResources(CoreUiR.color.primaryVariant)
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(CoreUiR.color.pageBackground)
        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.refresh()
        }
    }

    private fun initList(isFirstLoading: Boolean) {
        mBinding.newsList.adapter = mAdapter
        if (isFirstLoading) {
            mBinding.newsList.alpha = 0.3f
            mBinding.newsList.animate()
                .setDuration(600)
                .alpha(1f)
                .start()
        }
    }

    private fun subscribeToViewModel() {
        mViewModel.state.onEachWithLifecycle(viewLifecycleOwner, ::render)
    }

    private fun render(state: NewsListState) {
        mAdapter.submitList(state.items)

        (requireView().parent as? ViewGroup)?.doOnPreDraw {
            mBinding.newsList.layoutManager?.onRestoreInstanceState(state.scrollState)
            startPostponedEnterTransition()
        }
    }

    private fun buildContainerTransform(entering: Boolean) =
        MaterialContainerTransform(requireContext(), entering).apply {
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            duration = 600
            drawingViewId = R.id.card
            scrimColor = Color.TRANSPARENT
        }

    companion object {
        fun newInstance() = NewsListFragment()
    }

}