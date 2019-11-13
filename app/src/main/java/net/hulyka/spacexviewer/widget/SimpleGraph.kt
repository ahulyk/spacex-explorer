package net.hulyka.spacexviewer.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import net.hulyka.spacexviewer.toPx
import kotlin.math.min


class SimpleGraph @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    interface OnTouchListener {
        fun onTouch(v: View, value: Int)
    }

    var listeter: OnTouchListener? = null

    private val pathPaint = Paint().apply {
        color = Color.GREEN
        alpha = 64
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val rect = Rect()
    private val textPaint = TextPaint().apply {
        color = Color.GRAY
        textSize = 8.toPx
        isAntiAlias = true
    }

    private val drawData: MutableList<ItemData> = mutableListOf()
    private val inputData: MutableMap<String, Int> = mutableMapOf()
    private var pyRatio = 1f
    private var xGraphInitPos = 0f
    private var yGraphInitPos = 0f
    private var colWidth = 0f
    //size
    private var topPadding = 16.toPx
    private var bottomPadding = 32.toPx
    private var leftPadding = 16.toPx
    private var rightPadding = 16.toPx
    private var mihHeight = 64.toPx
    private var mihWidth = 96.toPx

    fun setGraphData(data: Map<String, Int>) {
        inputData.clear()
        inputData.putAll(data)
        postInvalidate()
    }

    private fun calcDrawData() {
        val max = inputData.values.max() ?: 1
        pyRatio = (height - bottomPadding - topPadding) / max
        xGraphInitPos = leftPadding
        yGraphInitPos = max * pyRatio + topPadding
        colWidth = (width - leftPadding - rightPadding) / (inputData.size)

        drawData.clear()
        var index = 0
        for (entry in inputData) {
            drawData.add(
                ItemData(
                    leftPadding + colWidth * index,
                    yGraphInitPos - entry.value * pyRatio,
                    entry.value,
                    entry.key
                )
            )
            ++index
        }
    }


    override fun onDraw(c: Canvas) {
        calcDrawData()
        drawBars(c)
        //TODO drawYText
        drawXText(c)
    }

    private fun drawBars(c: Canvas) {
        for (currentPos in drawData) {
            c.drawRect(
                currentPos.x + 1.toPx,
                currentPos.y,
                currentPos.x - 1.toPx + colWidth,
                yGraphInitPos,
                pathPaint
            )
        }
    }

    private fun drawXText(c: Canvas) {
        for (item in drawData) {
            val text = item.description
            textPaint.getTextBounds(text, 0, text.length, rect)
            val x = item.x + (colWidth - rect.width()) / 2f
            val y = yGraphInitPos + (bottomPadding - rect.height()) / 2f
            c.drawText(text, x, y, textPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val desiredWidth = (leftPadding + rightPadding + mihWidth).toInt()
        val desiredHeight = (topPadding + bottomPadding + mihHeight).toInt()

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize)
            else -> desiredWidth
        }

        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val height = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize)
            else -> desiredHeight
        }
        setMeasuredDimension(width, height)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            listeter?.let {
                if (drawData.isNotEmpty()) {
                    val item = drawData.lastOrNull { it.x < event.x } ?: drawData.first()
                    listeter?.onTouch(this, item.value)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    data class ItemData(val x: Float, val y: Float, val value: Int, val description: String)


}