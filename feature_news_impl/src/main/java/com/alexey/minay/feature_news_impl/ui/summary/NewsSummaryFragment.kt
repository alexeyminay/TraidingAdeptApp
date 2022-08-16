package com.alexey.minay.feature_news_impl.ui.summary

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.alexey.minay.core_navigation.Extras
import com.alexey.minay.core_ui.onEachWithLifecycle
import com.alexey.minay.core_ui.uiLazy
import com.alexey.minay.core_ui.viewBindings
import com.alexey.minay.core_ui.withArgs
import com.alexey.minay.feature_news_impl.R
import com.alexey.minay.feature_news_impl.databinding.FragmentNewsSummaryBinding
import com.alexey.minay.feature_news_impl.di.NewsComponent
import com.alexey.minay.feature_news_impl.domain.NewsId
import com.alexey.minay.feature_news_impl.presentation.summary.NewsSummaryState
import com.alexey.minay.feature_news_impl.presentation.summary.NewsSummaryViewModel
import com.alexey.minay.feature_news_impl.ui.SentimentLabelMapper
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
        initCardView(savedInstanceState == null)
        initList()
        initButton()
        subscribeToViewModel()

        requireActivity().onBackPressedDispatcher.addCallback {
            mViewModel.openNewsList(Extras(mBinding.itemGroup.root))
        }
    }

    private fun initCardView(isFirstLoading: Boolean) {
        if (isFirstLoading) {
            mBinding.infoCard.alpha = 0.1f
            mBinding.infoCard.animate()
                .setDuration(800)
                .alpha(1f)
                .start()
        }
    }

    private fun initList() {
        mBinding.tickers.adapter = mAdapter
        mBinding.tickers.layoutManager = FlowLayoutManager()
    }

    private fun initButton() = with(mBinding) {
        buttonGroup.button.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(requireContext(), CoreUiR.color.cardBackground)
        )
        buttonGroup.button.text = getText(CoreUiR.string.open_news)
        buttonGroup.button.setOnClickListener {
            mViewModel.openNews()
        }
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
        state.news?.overallSentimentLabel?.let {
            SentimentLabelMapper.map(
                sentimentLabel = it,
                icon = itemGroup.icon,
                subIcon = itemGroup.subIcon
            )
        }
    }

    private fun buildContainerTransform(entering: Boolean) =
        MaterialContainerTransform(requireContext(), entering).apply {
            interpolator = FastOutLinearInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            duration = 600
            drawingViewId = R.id.itemGroup
            scrimColor = Color.TRANSPARENT
        }

    companion object {

        private const val NEWS_ID = "new_id"
        fun newInstance(id: String) = NewsSummaryFragment().withArgs {
            putString(NEWS_ID, id)
        }
    }

}