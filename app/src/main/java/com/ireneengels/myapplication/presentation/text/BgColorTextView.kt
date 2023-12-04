package com.ireneengels.myapplication.presentation.text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.InputFilter
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.ireneengels.myapplication.R

class BgColorTextView : androidx.appcompat.widget.AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var drawableWidth: Int = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Calculate the drawable width (width - total horizontal margins)
        drawableWidth = w - (paddingLeft + paddingRight)
    }

    fun setMaxLength(maxLength: Int) {
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = InputFilter.LengthFilter(maxLength)
        filters = filterArray
    }

    override fun draw(canvas: Canvas) {
        val layout = layout ?: return

        val lineCount = layout.lineCount
        val rect = Rect()
        val paint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.white)
        }

        val extraPadding = 10

        for (i in 0 until lineCount) {
            rect.top = layout.getLineTop(i)
            rect.bottom = (layout.getLineBottom(i) - if (i + 1 == lineCount) 0f else layout.spacingAdd).toInt()

            rect.left = (layout.getLineLeft(i) + paddingLeft - extraPadding).toInt()
            rect.right = (layout.getLineRight(i) + paddingRight + extraPadding).toInt()



            canvas.drawRect(rect, paint)
        }

        super.draw(canvas)
    }
}
