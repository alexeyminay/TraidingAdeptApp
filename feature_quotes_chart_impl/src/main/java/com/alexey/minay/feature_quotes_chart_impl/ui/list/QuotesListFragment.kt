package com.alexey.minay.feature_quotes_chart_impl.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.core_ui.uiLazy
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.feature_quotes_chart_impl.R
import com.alexey.minay.feature_quotes_chart_impl.databinding.FragmentListBinding
import com.alexey.minay.feature_quotes_chart_impl.di.QuotesChartComponent
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesAction
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesStore
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.list.QuotesListState
import javax.inject.Inject
import com.alexey.minay.core_ui.R as CoreUiR

class QuotesListFragment : Fragment(R.layout.fragment_list) {

    private val mAdapter by uiLazy { QuotesListAdapter() }

    @Inject
    lateinit var store: QuotesStore
    private val mComponent get() = QuotesChartComponent.get()
    private val mBinding by viewBindings(FragmentListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        initSwipeRefreshLayout()
        initEmptyListButton()
        initList()
        subscribeToStore()
    }

    private fun initSwipeRefreshLayout() = with(mBinding) {
        swipeRefreshLayout.setColorSchemeResources(CoreUiR.color.primaryVariant)
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(CoreUiR.color.pageBackground)
        swipeRefreshLayout.setOnRefreshListener {
            store.accept(QuotesAction.RefreshQuotesList)
        }
    }

    private fun initEmptyListButton() {
        mBinding.emptyGroup.refreshButton.setOnClickListener {
            store.accept(QuotesAction.RefreshQuotesList)
        }
    }

    private fun initList() {
        mBinding.quotes.adapter = mAdapter
    }

    private fun subscribeToStore() {
        store.state.onEachWithLifecycle(viewLifecycleOwner, ::render)
    }

    private fun render(state: QuotesState) {
        mAdapter.submitList(state.listState.items)
        renderType(state.listState.type)
        renderRefreshLayout(state.listState.isRefreshing)
    }

    private fun renderType(type: QuotesListState.Type) = with(mBinding) {
        when (type) {
            QuotesListState.Type.INIT -> {
                quotes.isVisible = false
                loadingGroup.root.isVisible = true
                emptyGroup.root.isVisible = false
            }
            QuotesListState.Type.DATA -> {
                quotes.isVisible = true
                loadingGroup.root.isVisible = false
                emptyGroup.root.isVisible = false
            }
            QuotesListState.Type.EMPTY -> {
                quotes.isVisible = false
                loadingGroup.root.isVisible = false
                emptyGroup.root.isVisible = true
            }
        }
    }

    private fun renderRefreshLayout(isRefreshing: Boolean) {
        mBinding.swipeRefreshLayout.isRefreshing = isRefreshing
    }

    companion object {
        fun newInstance() = QuotesListFragment()
    }
}