package com.alexey.minay.tradingadeptapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.alexey.minay.tradingadeptapp.domain.Quotation

class QuotesChartView(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs), ScaleGestureDetector.OnScaleGestureListener {

    private var mQuotesChartViewState = QuotesChartViewState.default()
    private val mCandlestickPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        color = Color.GREEN
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2f
    }

    private var mCandleWidth = 24f
    private var mCandleMargin = 12f
    private val mCenterCandleInterval get() = mCandleWidth + mCandleMargin
    private var mFirstVisibleCandleIndex = 0
    private var mFirstVisibleCandlePositionX: Float? = null
    private var mMaxMinPair = MutableMaxMinPair(Float.MAX_VALUE, Float.MIN_VALUE)

    private val mDetector = ScaleGestureDetector(context, this)

    fun setValue(quotes: List<Quotation>) {
        mQuotesChartViewState = mQuotesChartViewState.copy(quotes = quotes)
        invalidate()
    }

    var previousValue = 0f

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
            firstVisibleCandlePositionX += (event.x - previousValue)
            if ((firstVisibleCandlePositionX - mCandleWidth * 2) > width) {
                mFirstVisibleCandleIndex++
                firstVisibleCandlePositionX -= (mCandleWidth + mCandleMargin)
            } else if ((firstVisibleCandlePositionX + mCandleWidth) < width && mFirstVisibleCandleIndex > 0) {
                mFirstVisibleCandleIndex--
                firstVisibleCandlePositionX += (mCandleWidth + mCandleMargin)
            }
            mFirstVisibleCandlePositionX = firstVisibleCandlePositionX
            previousValue = event.x
            invalidate()
        }

        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mFirstVisibleCandlePositionX == null) {
            mFirstVisibleCandlePositionX = width.toFloat() / 2
        }

        val firstVisibleCandlePositionX = mFirstVisibleCandlePositionX ?: return

        val maxCandleCount = (width / (mCandleWidth + mCandleMargin)).toInt()

        val candleCount = ((firstVisibleCandlePositionX / width) * maxCandleCount).toInt() + 1

        val lastVisibleIndex = mFirstVisibleCandleIndex + candleCount
        mQuotesChartViewState.findMaxAndMin(mMaxMinPair, mFirstVisibleCandleIndex, lastVisibleIndex)

        var drawXPos = firstVisibleCandlePositionX
        mQuotesChartViewState.quotes.forEachIndexed { index, quotation ->
            if (index >= mFirstVisibleCandleIndex) {
                val top: Float
                val bottom: Float
                if (quotation.close >= quotation.open) {
                    mCandlestickPaint.color = Color.GREEN
                    top = quotation.close
                    bottom = quotation.open
                } else {
                    mCandlestickPaint.color = Color.RED
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

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        Log.d("QuotesChartView", "QuotesChartView ${detector.scaleFactor}")
        mCandleWidth *= detector.scaleFactor
        mCandleMargin *= detector.scaleFactor
        invalidate()
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean = true

    override fun onScaleEnd(detector: ScaleGestureDetector?) = Unit

    private fun Float.extrapolate(y1: Float, y2: Float, x1: Float, x2: Float) =
        y1 + (y2 - y1) / (x2 - x1) * (this - x1)

}