package com.alexey.minay.feature_menu_impl

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alexey.minay.core_navigation.Extras
import com.alexey.minay.core_navigation.MainMenuItem
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.feature_menu_impl.databinding.FragmentMenuBinding
import com.alexey.minay.feature_menu_impl.di.MenuComponentHolder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val mComponentHolder by viewModels<MenuComponentHolder>()
    private val mViewModel by viewModels<MenuViewModel> {
        mComponentHolder.component.viewModelProviderFactory
    }
    private val mBinding by viewBindings(FragmentMenuBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
        subscribeToViewModel()
    }

    private fun initMenu() {
        mBinding.menu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.quotes -> mViewModel.openQuotesList()
                R.id.chart -> mViewModel.openQuotesChart()
                R.id.news -> mViewModel.openNewsList()
            }

            return@setOnItemSelectedListener true
        }
    }

    private fun subscribeToViewModel() {
        mViewModel.menuFragmentFlow
            .onEachWithLifecycle(viewLifecycleOwner) {
                openFragment(it)
            }
        mViewModel.selectedMenuItem
            .onEachWithLifecycle(viewLifecycleOwner) { menuItem ->
                mBinding.menu.selectedItemId = when (menuItem) {
                    MainMenuItem.QUOTES_LIST -> R.id.quotes
                    MainMenuItem.QUOTES_CHART -> R.id.chart
                    MainMenuItem.NEWS_LIST -> R.id.news
                }
            }
    }

    private fun openFragment(pair: Pair<Fragment?, Extras?>) {
        val fragment = pair.first
        if (fragment == null) {
            childFragmentManager.popBackStack()
            return
        }

        childFragmentManager.commit {
            pair.second?.let {
                setReorderingAllowed(true)
                addSharedElement(it.sharedView, it.sharedView.transitionName)
            }
            replace(R.id.fragmentContainer, fragment, null)
        }
    }

    companion object {

        fun newInstance() = MenuFragment()

    }

}