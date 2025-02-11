package com.example.ebot.common

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ebot.R


class SwipeButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
    defStyleRes: Int = -1
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    private lateinit var slidingButton: ImageView
    private var initialX: Float = 0f
    private var active: Boolean = false
    private var initialButtonWidth: Int = 0
    private lateinit var centerText: TextView

    private var disabledDrawable: Drawable? = null
    private var enabledDrawable: Drawable? = null

    init {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val background = this

        val layoutParamsView = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        }

        background.setBackground(ContextCompat.getDrawable(context, R.drawable.bike1))
        addView(background, layoutParamsView)

        val centerText = TextView(context)
        this.centerText = centerText
        centerText.gravity = Gravity.CENTER

        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        }

        centerText.text = "SWIPE" // Add any text you need
        centerText.setTextColor(Color.WHITE)
        background.addView(centerText, layoutParams)

        val swipeButton = ImageView(context)
        this.slidingButton = swipeButton

        val layoutParamsButton = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE)
            addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        }

        swipeButton.setBackground(ContextCompat.getDrawable(context, R.drawable.button_gray_bg))
        swipeButton.setImageDrawable(disabledDrawable)
        addView(swipeButton, layoutParamsButton)

        setOnTouchListener(getButtonTouchListener())
    }

    // Other methods, if needed, can go here.
    private fun getButtonTouchListener(): OnTouchListener? {
        return OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> return@OnTouchListener true
                MotionEvent.ACTION_MOVE ->
                    //Movement logic here
                    return@OnTouchListener true

                MotionEvent.ACTION_UP ->
                    //Release logic here
                    return@OnTouchListener true
            }
            false
        }
    }
}
