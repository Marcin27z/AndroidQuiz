package com.example.quiz

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.popup_window.view.*

class PopUp(private val view: View, context: Context): PopupWindow(view) {

    init {
        contentView = View.inflate(context, R.layout.popup_window, null)
    }

    fun show(listener: OnTouchListener, text: String) {
        setTouchInterceptor(View.OnTouchListener { _, _ ->
            listener.tap()
            return@OnTouchListener true
        })
        isOutsideTouchable = true
        animationStyle = R.style.Animation
        height = 500
        width = 600
        val message = contentView.message
        message.text = text
        message.textSize = 50.0F
        message.gravity = Gravity.CENTER
        showAtLocation(view, Gravity.CENTER, 0, 0)
    }

    interface OnTouchListener {
        fun tap()
    }
}