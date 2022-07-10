package com.alexey.minay.tradingadeptapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.content.ContextCompat
import com.alexey.minay.tradingadeptapp.domain.Quotation

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
            if ((firstVisibleCandlePositionX - mCandleWidth) > width) {
                mFirstVisibleCandleIndex++
                firstVisibleCandlePositionX -= (mCandleWidth + mCandleMargin)
            } else if ((firstVisibleCandlePositionX + mCandleWidth) < width && mFirstVisibleCandleIndex > 0) {
                val count = ((width - firstVisibleCandlePositionX) /
                        (mCandleWidth + mCandleMargin)).toInt()
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
        drawCandles(canvas)
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

    private fun drawCandles(canvas: Canvas) {
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
                if (quotation.isGreen()) {
                    mCandlestickPaint.color = ContextCompat.getColor(context, R.color.green)
                    top = quotation.close
                    bottom = quotation.open
                } else {
                    mCandlestickPaint.color = ContextCompat.getColor(context, R.color.red)
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
            lastCandle.isGreen() -> ContextCompat.getColor(context, R.color.green)
            else -> ContextCompat.getColor(context, R.color.red)
        }

        val y = height - lastCandle.close.extrapolate(
            0f,
            height.toFloat(),
            mMaxMinPair.min,
            mMaxMinPair.max
        )
        drawLine(0f, y, height.toFloat(), y, mDotsLinePaint)
    }

    private fun Quotation.isGreen() = close >= open

    private fun Float.extrapolate(y1: Float, y2: Float, x1: Float, x2: Float) =
        y1 + (y2 - y1) / (x2 - x1) * (this - x1)

}