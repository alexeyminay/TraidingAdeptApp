package com.alexey.minay.feature_menu_impl

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.alexey.minay.core_navigation.Extras
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.feature_menu_impl.di.MenuComponentHolder
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val mComponentHolder by viewModels<MenuComponentHolder>()
    private val mViewModel by viewModels<MenuViewModel> {
        mComponentHolder.component.viewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
        subscribeToViewModel()
    }

    private fun initMenu() {
        requireView().findViewById<BottomNavigationView>(R.id.menu).setOnItemSelectedListener {
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
            //.distinctUntilFragmentChanged()
            .onEachWithLifecycle(viewLifecycleOwner) {
            openFragment(it)
        }
    }

    private fun openFragment(pair: Pair<Fragment?, Extras?>) {
        val fragment = pair.first
        if (fragment == null) {
            childFragmentManager.popBackStack()
            return
        }

        childFragmentManager.commit {
            pair.second?.let { addSharedElement(it.sharedView, it.sharedView.transitionName) }
            replace(R.id.fragmentContainer, fragment, null)
        }
    }

    companion object {

        fun newInstance() = MenuFragment()

    }

}