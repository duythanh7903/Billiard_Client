package com.datn.bia.a.common.base.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

fun View.goneView() {
    if (isVisible || isInvisible) visibility = View.GONE
}

fun View.visibleView() {
    if (isInvisible || isGone) visibility = View.VISIBLE
}

fun View.invisibleView() {
    if (isVisible) visibility = View.INVISIBLE
}

internal const val DISPLAY = 1080
fun View.resizeView(width: Int, height: Int = 0) {
    val pW = context.getWidthScreenPx() * width / DISPLAY
    val pH = if (height == 0) pW else pW * height / width
    val params = layoutParams
    params.let {
        it.width = pW
        it.height = pH
    }
}

fun View.setBackgroundColorResource(context: Context, @ColorRes color: Int) {
    this.setBackgroundColor(
        ContextCompat.getColor(context, color)
    )
}

private var lastClickTime: Long = 0
private var timeDelayDefault = 300L
private fun onNextEventActionAfterTimeDelay(timeDelay: Long, view: View, action: (view: View) -> Unit) {
    if (SystemClock.elapsedRealtime() - lastClickTime < timeDelay) return
    else action.invoke(view)
    lastClickTime = SystemClock.elapsedRealtime()
}

fun View.click(timeDelay: Long = timeDelayDefault, action: (view: View?) -> Unit) {
    this.setOnClickListener { view ->
        onNextEventActionAfterTimeDelay(timeDelay, view, action = {
            action.invoke(view)
        })
    }
}

fun View.clickZoom(timeDelay: Long = timeDelayDefault, action: (view: View?) -> Unit) {
    this.setOnCustomTouchViewScale(object: OnCustomClickListener {
        override fun onCustomClick(view: View, event: MotionEvent) {
            onNextEventActionAfterTimeDelay(timeDelay, view, action = {
                action.invoke(view)
            })
        }
    })
}

fun View.clickOpacity(timeDelay: Long = timeDelayDefault, action: (view: View?) -> Unit) {
    this.setOnCustomTouchViewAlphaNotOther(object: OnCustomClickListener {
        override fun onCustomClick(view: View, event: MotionEvent) {
            onNextEventActionAfterTimeDelay(timeDelay, view, action = {
                action.invoke(view)
            })
        }
    })
}

fun View.toBitmap(): Bitmap? {
    if (width > 0 && height > 0) {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }

    return null
}

fun View.isKeyboardVisible(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val insets = rootWindowInsets
        insets?.isVisible(WindowInsets.Type.ime()) ?: false
    } else {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        return inputMethodManager?.isActive(this) ?: false
    }
}