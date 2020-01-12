package com.deemons.baselib.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import com.blankj.utilcode.util.SpanUtils

/**
 * author： deemo
 * date:    2019-10-22
 * desc:    背景倒圆角
 */
class RoundBackgroundSpan : ReplacementSpan {
    private var backgroundColor = 0
    private var textColor = 0
    private var radius = 0

    constructor(backgroundColor: Int = 0, textColor: Int = 0, radius: Int = 8) : super() {
        this.backgroundColor = backgroundColor
        this.textColor = textColor
        this.radius = if (radius < 0) 0 else radius
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val paintColor = paint.color
        val fm = paint.fontMetrics
        //        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end), bottom);
        // fm.bottom - fm.top 解决设置行距（android:lineSpacingMultiplier="1.2"）时背景色高度问题
        val rect =
            RectF(x, top.toFloat(), x + measureText(paint, text, start, end), fm.bottom - fm.top)
        if (backgroundColor != 0) paint.color = backgroundColor
        canvas.drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)

        paint.color = if (textColor != 0) textColor else paintColor
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return Math.round(paint.measureText(text, start, end))
    }

    private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float {
        return paint.measureText(text, start, end)
    }


}


fun SpanUtils.setRoundBackground(
    backgroundColor: Int = 0,
    textColor: Int = 0,
    radius: Int = 8
): SpanUtils {
    this.setSpans(RoundBackgroundSpan(backgroundColor, textColor, radius))
        .append(" ")
    return this
}