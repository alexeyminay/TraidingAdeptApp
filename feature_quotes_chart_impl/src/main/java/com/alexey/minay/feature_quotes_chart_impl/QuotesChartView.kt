package com.alexey.minay.feature_quotes_chart_impl

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.content.ContextCompat
import com.alexey.minay.feature_quotes_chart_impl.domain.Quotation
import kotlin.math.abs
import com.alexey.minay.core_ui.R as CoreuiR

class QuotesChartView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs), ScaleGestureDetector.OnScaleGestureListener {

    private var mQuotesChartViewState = QuotesChartViewState.default()
    private val mCandlestickPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2f
    }

    private val mDotsLinePaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)
        strokeWidth = 1f
    }

    private val mGridLinePaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.STROKE
        strokeWidth = 1f
        color = ContextCompat.getColor(context, CoreuiR.color.grey)
    }

    private var mCandleWidth = 24f
    private var mCandleMargin = 12f
    private val mCenterCandleInterval get() = mCandleWidth + mCandleMargin
    private var mFirstVisibleCandleIndex = 0
    private var mFirstVisibleCandlePositionX: Float? = null
    private var mMaxMinPair = MutableMaxMinPair(Float.MAX_VALUE, Float.MIN_VALUE)
    private val mDetector = ScaleGestureDetector(context, this)

    private val mWidth: Float
        get() = width.toFloat() - resources.getDimensionPixelSize(R.dimen.chart_margin)

    fun setValue(quotes: List<Quotation>) {
        if (mQuotesChartViewState.quotes == quotes) {
            return
        }

        mQuotesChartViewState = mQuotesChartViewState.copy(quotes = quotes)
        invalidate()
    }

    var previousValue = 0f

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(SUPER_STATE, super.onSaveInstanceState())
        bundle.putInt(INDEX, mFirstVisibleCandleIndex)
        bundle.putSerializable(STATE, mQuotesChartViewState)
        mFirstVisibleCandlePositionX?.let { bundle.putFloat(POSITION, it) }
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        var restoredState = state
        if (state is Bundle) {
            mFirstVisibleCandleIndex = state.getInt(INDEX)
            //mFirstVisibleCandlePositionX = state.getFloat(POSITION)
            mQuotesChartViewState = state.getSerializable(STATE) as QuotesChartViewState
            mMaxMinPair.reset()

            restoredState = state.getParcelable(SUPER_STATE)
            invalidate()
        }
        super.onRestoreInstanceState(restoredState)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var firstVisibleCandlePositionX = mFirstVisibleCandlePositionX ?: return false

        if (event.pointerCount == 2) {
            mDetector.onTouchEvent(event)
            return true
        }

        if (event.pointerCount > 1) {
            return false
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            previousValue = event.x
        }

        if (event.action == MotionEvent.ACTION_MOVE) {
            val moveValue = event.x - previousValue
            val lastVisibleIndex = findLastVisibleIndex(firstVisibleCandlePositionX)
            val cantMoveLeft = moveValue < 0 &&
                    ((mFirstVisibleCandlePositionX!! - abs(moveValue)) < mCenterCandleInterval * 7)

            val cantMoveRight =
                moveValue > 0 && lastVisibleIndex == mQuotesChartViewState.quotes.lastIndex
            Log.d(
                "QuotesChartView",
                "$mFirstVisibleCandleIndex $lastVisibleIndex $firstVisibleCandlePositionX $moveValue"
            )
            if (cantMoveLeft || cantMoveRight) {
                return true
            }

            firstVisibleCandlePositionX += moveValue
            if ((firstVisibleCandlePositionX - mCandleWidth) > mWidth) {
                val count = ((firstVisibleCandlePositionX - mWidth) /
                        (mCandleWidth + mCandleMargin)).toInt()
                mFirstVisibleCandleIndex += count
                firstVisibleCandlePositionX -= (mCandleWidth + mCandleMargin) * count
            } else if ((firstVisibleCandlePositionX + mCandleWidth) < mWidth && mFirstVisibleCandleIndex > 0) {
                val count = ((mWidth - firstVisibleCandlePositionX) /
                        (mCandleWidth + mCandleMargin)).toInt()
                    .takeIf { it - mFirstVisibleCandleIndex < 0 } ?: 0
                mFirstVisibleCandleIndex -= count
                firstVisibleCandlePositionX += (mCandleWidth + mCandleMargin) * count
            }
            mFirstVisibleCandlePositionX = firstVisibleCandlePositionX
            previousValue = event.x
            invalidate()
        }

        return true
    }

    override fun onDraw(canvas: Canvas) = with(canvas) {
        super.onDraw(canvas)
        if (mFirstVisibleCandlePositionX == null) {
            mFirstVisibleCandlePositionX = mWidth
        }

        val firstVisibleCandlePositionX = mFirstVisibleCandlePositionX ?: return
        val lastVisibleIndex = findLastVisibleIndex(firstVisibleCandlePositionX)
        mQuotesChartViewState.findMaxAndMin(mMaxMinPair, mFirstVisibleCandleIndex, lastVisibleIndex)

        drawGrid()
        drawCandles(canvas, firstVisibleCandlePositionX, lastVisibleIndex)
        drawLastValueLine()
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        mCandleWidth *= detector.scaleFactor
        mCandleMargin *= detector.scaleFactor
        invalidate()
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean = true

    override fun onScaleEnd(detector: ScaleGestureDetector?) = Unit

    private fun drawCandles(
        canvas: Canvas,
        firstVisibleCandlePositionX: Float,
        lastVisibleIndex: Int
    ) {
        var drawXPos = firstVisibleCandlePositionX

        mQuotesChartViewState.quotes.forEachIndexed { index, quotation ->
            if (index >= mFirstVisibleCandleIndex) {
                val top: Float
                val bottom: Float
                if (quotation.isGreen()) {
                    mCandlestickPaint.color = ContextCompat.getColor(context, CoreuiR.color.green)
                    top = quotation.close
                    bottom = quotation.open
                } else {
                    mCandlestickPaint.color = ContextCompat.getColor(context, CoreuiR.color.red)
                    bottom = quotation.close
                    top = quotation.open
                }

                canvas.drawLine(
                    drawXPos,
                    height - quotation.low.extrapolate(
                        0f,
                        height.toFloat(),
                        mMaxMinPair.min,
                        mMaxMinPair.max
                    ),
                    drawXPos,
                    height - quotation.high.extrapolate(
                        0f,
                        height.toFloat(),
                        mMaxMinPair.min,
                        mMaxMinPair.max
                    ),
                    mCandlestickPaint
                )

                canvas.drawRect(
                    drawXPos - mCandleWidth / 2,
                    height - top.extrapolate(
                        0f,
                        height.toFloat(),
                        mMaxMinPair.min,
                        mMaxMinPair.max
                    ),
                    drawXPos + mCandleWidth / 2,
                    height - bottom.extrapolate(
                        0f,
                        height.toFloat(),
                        mMaxMinPair.min,
                        mMaxMinPair.max
                    ),
                    mCandlestickPaint
                )

                drawXPos -= mCenterCandleInterval

                if (index > lastVisibleIndex) {
                    return
                }
            }
        }
    }

    private fun Canvas.drawLastValueLine() {
        val lastCandle = mQuotesChartViewState.quotes.firstOrNull() ?: return

        if (lastCandle.close < mMaxMinPair.min && lastCandle.close > mMaxMinPair.max) {
            return
        }

        mDotsLinePaint.color = when {
            lastCandle.isGreen() -> ContextCompat.getColor(context, CoreuiR.color.green)
            else -> ContextCompat.getColor(context, CoreuiR.color.red)
        }

        val y = height - lastCandle.close.extrapolate(
            0f,
            height.toFloat(),
            mMaxMinPair.min,
            mMaxMinPair.max
        )
        drawLine(0f, y, mWidth, y, mDotsLinePaint)
    }

    private fun Canvas.drawGrid() {
        val stepY = mMaxMinPair.div / 16
        repeat(16) {
            val y = (mMaxMinPair.min + it * stepY).extrapolate(
                0f,
                height.toFloat(),
                mMaxMinPair.min,
                mMaxMinPair.max
            )
            drawLine(0f, y, mWidth, y, mGridLinePaint)
        }
    }

    private fun Quotation.isGreen() = close >= open

    private fun findLastVisibleIndex(firstVisibleCandlePositionX: Float): Int {
        val maxCandleCount = (mWidth / (mCandleWidth + mCandleMargin)).toInt()
        val candleCount = ((firstVisibleCandlePositionX / mWidth) * maxCandleCount).toInt() + 1
        return mFirstVisibleCandleIndex + candleCount
    }

    private fun Float.extrapolate(y1: Float, y2: Float, x1: Float, x2: Float) =
        y1 + (y2 - y1) / (x2 - x1) * (this - x1)

    companion object {
        private const val SUPER_STATE = "super_state"
        private const val INDEX = "index"
        private const val STATE = "state"
        private const val POSITION = "position"
    }

}