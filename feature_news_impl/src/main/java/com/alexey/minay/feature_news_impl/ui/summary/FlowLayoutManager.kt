package com.alexey.minay.feature_news_impl.ui.summary

import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView

class FlowLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams() = RecyclerView.LayoutParams(
        RecyclerView.LayoutParams.WRAP_CONTENT,
        RecyclerView.LayoutParams.WRAP_CONTENT
    )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        recycler ?: return
        detachAndScrapAttachedViews(recycler)
        addViews(recycler)
    }

    override fun isAutoMeasureEnabled() = true

    private fun addViews(recycler: RecyclerView.Recycler) {
        var top = 0
        var bottom = 0
        var left = 0
        var right = 0
        var spaceBeforeEnd = width
        var previousHeight = 0
        var lines = when (itemCount > 0) {
            true -> 1
            else -> 0
        }

        repeat(itemCount) { position ->
            val child = recycler.getViewForPosition(position)
            addView(child)
            measureChildWithMargins(child, 0, 0)

            val childWidth = child.measuredWidth + child.marginStart + child.marginEnd
            val childHeight = child.measuredHeight + child.marginBottom + child.marginTop

            if (spaceBeforeEnd - childWidth >= 0) {
                right += childWidth
                bottom = bottom - previousHeight + childHeight
            } else {
                lines++
                spaceBeforeEnd = width
                left = 0
                right = childWidth
                bottom += childHeight
                top += previousHeight
            }
            layoutDecoratedWithMargins(child, left, top, right, bottom)
            left += childWidth

            spaceBeforeEnd -= childWidth
            previousHeight = childHeight
        }
    }

}