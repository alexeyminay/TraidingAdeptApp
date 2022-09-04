package com.alexey.minay.feature_quotes_chart_impl

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.content.ContextCompat
import com.alexey.minay.feature_quotes_chart_impl.domain.Quotation
import kotlin.math.abs
import kotlin.math.roundToInt
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

    private val mBackgroundPaint = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context, CoreuiR.color.pageBackground)
    }

    private val mValuePaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.FILL
    }

    private val mValueStrokePaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.STROKE
        strokeWidth = 1f
        strokeCap = Paint.Cap.ROUND
    }

    private val mValueTextPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        textSize = resources.getDimensionPixelSize(R.dimen.chartValueTextSize).toFloat()
        textAlign = Paint.Align.LEFT
    }
    private val mFontMetrics = Paint.FontMetrics()

    private var mCandleWidth = 16f
    private var mCandleMargin = 8f
    private val mCenterCandleInterval get() = mCandleWidth + mCandleMargin
    private var mFirstVisibleCandleIndex = 0
    private var mFirstVisibleCandlePositionX: Float? = null
    private var mMaxMinPair = MutableMaxMinPair(Float.MAX_VALUE, Float.MIN_VALUE)
    private val mDetector = ScaleGestureDetector(context, this)
    private val mValueTextMargin = 1.7f * (mCandleWidth + mCandleMargin)

    private val mMarginEnd = resources.getDimensionPixelSize(R.dimen.chart_margin_end)
    private val mMarginVertical = resources.getDimensionPixelSize(R.dimen.chart_margin_vertical)
    private val mWidth: Float
        get() = width.toFloat() - mMarginEnd

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
//            Log.d(
//                "QuotesChartView",
//                "$mFirstVisibleCandleIndex $lastVisibleIndex $firstVisibleCandlePositionX $moveValue"
//            )
            if (cantMoveLeft || cantMoveRight) {
                return true
            }

            firstVisibleCandlePositionX += moveValue
            if ((firstVisibleCandlePositionX - mCandleWidth) > mWidth) {
                val count = ((firstVisibleCandlePositionX - mWidth) /
                        (mCandleWidth + mCandleMargin)).toInt()

                mFirstVisibleCandleIndex += count
                firstVisibleCandlePositionX -= (mCandleWidth + mCandleMargin) * count
            } else if ((firstVisibleCandlePositionX) < mWidth && mFirstVisibleCandleIndex > 0) {
                val count = ((mWidth - firstVisibleCandlePositionX) /
                        (mCandleWidth + mCandleMargin)).toInt()
                //.takeIf { it - mFirstVisibleCandleIndex <= 0 } ?: 0

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
        drawFirstVisibleCandleValue()
        drawLastValueLine()
        drawBackground()
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
                        0f + mMarginVertical,
                        height.toFloat() - mMarginVertical,
                        mMaxMinPair.min,
                        mMaxMinPair.max
                    ),
                    drawXPos,
                    height - quotation.high.extrapolate(
                        0f + mMarginVertical,
                        height.toFloat() - mMarginVertical,
                        mMaxMinPair.min,
                        mMaxMinPair.max
                    ),
                    mCandlestickPaint
                )

                canvas.drawRect(
                    drawXPos - mCandleWidth / 2,
                    height - top.extrapolate(
                        0f + mMarginVertical,
                        height.toFloat() - mMarginVertical,
                        mMaxMinPair.min,
                        mMaxMinPair.max
                    ),
                    drawXPos + mCandleWidth / 2,
                    height - bottom.extrapolate(
                        0f + mMarginVertical,
                        height.toFloat() - mMarginVertical,
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
            0f + mMarginVertical,
            height.toFloat() - mMarginVertical,
            mMaxMinPair.min,
            mMaxMinPair.max
        )
        drawLine(0f, y, mWidth, y, mDotsLinePaint)

        mValuePaint.color = when {
            lastCandle.isGreen() -> ContextCompat.getColor(context, CoreuiR.color.green)
            else -> ContextCompat.getColor(context, CoreuiR.color.red)
        }

        mValueTextPaint.getFontMetrics(mFontMetrics)

        val margin = 10f
        drawRect(
            mWidth + mValueTextMargin,
            y + margin,
            mWidth + mValueTextMargin + mValueTextPaint.measureText(lastCandle.close.toString()) + 2 * margin,
            y - resources.getDimensionPixelSize(R.dimen.chartValueTextSize).toFloat() - margin / 2,
            mValuePaint
        )

        mValueTextPaint.color = ContextCompat.getColor(context, CoreuiR.color.title_1)

        drawText(
            lastCandle.close.toString(),
            margin + mWidth + mValueTextMargin,
            y,
            mValueTextPaint
        )
    }

    private fun Canvas.drawGrid() {
        val maxHorizontalLines = 12
        val minHorizontalLines = 4
        val canMax = (height - mMarginVertical * 2) / maxHorizontalLines >
                resources.getDimensionPixelSize(R.dimen.minLengthBetweenLines)

        val horizontalLineCount = if (canMax) maxHorizontalLines
        else minHorizontalLines

        val stepY = mMaxMinPair.div / horizontalLineCount
        repeat(horizontalLineCount + 1) {
            val value = mMaxMinPair.min + it * stepY
            val y = value.extrapolate(
                0f + mMarginVertical,
                height.toFloat() - mMarginVertical,
                mMaxMinPair.max,
                mMaxMinPair.min
            )
            drawLine(0f, y, mWidth, y, mGridLinePaint)

            mValueTextPaint.color = ContextCompat.getColor(context, CoreuiR.color.subtitle_1)

            val roundedValue = ((value * 10000).roundToInt()).toFloat() / 10000
            drawText(
                roundedValue.toString(),
                mWidth + mValueTextMargin + 10f,
                y,
                mValueTextPaint
            )
        }
    }

    private fun Canvas.drawFirstVisibleCandleValue() {
        val firstVisibleCandle = mQuotesChartViewState.quotes[mFirstVisibleCandleIndex]
        mValueTextPaint.color = when {
            firstVisibleCandle.isGreen() -> ContextCompat.getColor(context, CoreuiR.color.green)
            else -> ContextCompat.getColor(context, CoreuiR.color.red)
        }

        val value = firstVisibleCandle.close

        val y = value.extrapolate(
            0f + mMarginVertical,
            height.toFloat() - mMarginVertical,
            mMaxMinPair.max,
            mMaxMinPair.min
        )

        mValueTextPaint.getFontMetrics(mFontMetrics)

        val margin = 10f

        mValuePaint.color = ContextCompat.getColor(context, CoreuiR.color.pageBackground)

        drawRect(
            mWidth + mValueTextMargin,
            y + margin,
            width.toFloat(),
            y - resources.getDimensionPixelSize(R.dimen.chartValueTextSize).toFloat() - margin / 2,
            mValuePaint
        )

        mValueStrokePaint.color = when {
            firstVisibleCandle.isGreen() -> ContextCompat.getColor(context, CoreuiR.color.green)
            else -> ContextCompat.getColor(context, CoreuiR.color.red)
        }

        drawRect(
            mWidth + mValueTextMargin + 1,
            y + margin,
            mWidth + mValueTextMargin + mValueTextPaint.measureText(value.toString()) + 2 * margin,
            y - resources.getDimensionPixelSize(R.dimen.chartValueTextSize).toFloat() - margin / 2,
            mValueStrokePaint
        )

        drawText(
            value.toString(),
            mWidth + mValueTextMargin + 10f,
            y,
            mValueTextPaint
        )
    }

    private fun Canvas.drawBackground() {
        drawLine(mWidth, 0f, mWidth, height.toFloat(), mGridLinePaint)
        drawRect(
            mWidth,
            0f,
            mWidth + mValueTextMargin,
            height.toFloat(),
            mBackgroundPaint
        )
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