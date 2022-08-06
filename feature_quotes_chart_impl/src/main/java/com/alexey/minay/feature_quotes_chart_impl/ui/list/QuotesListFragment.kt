package com.alexey.minay.feature_quotes_chart_impl.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.core_ui.uiLazy
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.feature_quotes_chart_impl.R
import com.alexey.minay.feature_quotes_chart_impl.databinding.FragmentListBinding
import com.alexey.minay.feature_quotes_chart_impl.di.QuotesChartComponent
import com.alexey.minay.feature_quotes_chart_impl.presentation.QuotesStore
import com.alexey.minay.feature_quotes_chart_impl.presentation.state.QuotesState
import javax.inject.Inject

class QuotesListFragment : Fragment(R.layout.fragment_list) {

    private val mAdapter by uiLazy { QuotesListAdapter() }

    @Inject
    lateinit var store: QuotesStore
    private val mComponent get() = QuotesChartComponent.get()
    private val mBinding by viewBindings(FragmentListBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        initList()
        subscribeToStore()
    }

    private fun initList() {
        mBinding.quotes.adapter = mAdapter
    }

    private fun subscribeToStore() {
        store.state.onEachWithLifecycle(viewLifecycleOwner, ::render)
    }

    private fun render(state: QuotesState) {
        mAdapter.submitList(state.listState.items)
    }

    companion object {
        fun newInstance() = QuotesListFragment()
    }
}