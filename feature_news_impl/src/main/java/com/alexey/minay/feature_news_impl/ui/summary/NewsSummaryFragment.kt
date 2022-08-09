package com.alexey.minay.feature_news_impl.ui.summary

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.core_ui.uiLazy
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.core_ui.withArgs
import com.alexey.minay.feature_news_impl.R
import com.alexey.minay.feature_news_impl.databinding.FragmentNewsSummaryBinding
import com.alexey.minay.feature_news_impl.di.NewsComponent
import com.alexey.minay.feature_news_impl.domain.NewsId
import com.alexey.minay.feature_news_impl.presentation.news.NewsSummaryState
import com.alexey.minay.feature_news_impl.presentation.news.NewsSummaryViewModel
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialContainerTransform
import com.alexey.minay.core_ui.R as CoreUiR

class NewsSummaryFragment : Fragment(R.layout.fragment_news_summary) {

    private val mNewsId get() = arguments?.getString(NEWS_ID) ?: throw IllegalArgumentException()
    private val mBinding by viewBindings(FragmentNewsSummaryBinding::bind)
    private val mAdapter by uiLazy { TickerAdapter() }
    private val mViewModel by viewModels<NewsSummaryViewModel> {
        NewsComponent.get().viewModelProviderFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = buildContainerTransform(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.itemGroup.root.transitionName = mNewsId
        super.onViewCreated(view, savedInstanceState)
        savedInstanceState ?: mViewModel.fetchNews(NewsId(mNewsId))
        initList()
        initButton()
        subscribeToViewModel()

        requireActivity().onBackPressedDispatcher.addCallback {
            Toast.makeText(requireContext(), "asdf", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initList() {
        mBinding.tickers.adapter = mAdapter
        mBinding.tickers.layoutManager = FlowLayoutManager()
    }

    private fun initButton() = with(mBinding) {
        button.refreshButton.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), CoreUiR.color.cardBackground)
        )
        button.refreshButton.text = getText(CoreUiR.string.open_news)
    }

    private fun subscribeToViewModel() {
        mViewModel.state.onEachWithLifecycle(viewLifecycleOwner, ::render)
    }

    private fun render(state: NewsSummaryState) = with(mBinding) {
        newsTitle.text = state.news?.title
        itemGroup.title.text = state.news?.title
        subtitle.text = state.news?.summary
        itemGroup.subTitle.text = state.news?.summary
        author.text = getString(CoreUiR.string.authors).format(
            state.news?.authors?.foldIndexed("") { index, prev, next ->
                val separator = when (index) {
                    0 -> ""
                    else -> ", "
                }
                "$prev$separator$next"
            } ?: ""
        )
        Glide.with(requireContext())
            .load(state.news?.thumbnailUrl)
            .error(R.drawable.ic_default_thumbnail)
            .into(itemGroup.image)
        mAdapter.submitList(state.news?.tickers ?: emptyList())
    }

    private fun buildContainerTransform(entering: Boolean) =
        MaterialContainerTransform(requireContext(), entering).apply {
            drawingViewId = com.alexey.minay.feature_menu_impl.R.id.fragmentContainer
            interpolator = FastOutSlowInInterpolator()
//            containerColor = MaterialColors
//                .getColor(requireActivity().findViewById(android.R.id.content), R.attr.colorSurface)
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            duration = 300
        }

    companion object {
        private const val NEWS_ID = "new_id"

        fun newInstance(id: String) = NewsSummaryFragment().withArgs {
            putString(NEWS_ID, id)
        }
    }

}