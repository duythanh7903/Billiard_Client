package com.datn.bia.a.common.base.ext

import android.graphics.Paint
import android.text.SpannableString
import android.text.TextPaint
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.URLSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener

fun TextView.setTextById(idString: Int) {
    text = resources.getString(idString)
}

fun TextView.setTextByString(string: String) {
    text = string
}

val TextView.value: String get() = text.toString().trim()

fun TextView.underlineText() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}

fun TextView.removeUnderlines() {
    val spannable = SpannableString(text)
    for (u in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
        spannable.setSpan(object : URLSpan(u.url) {
            override fun updateDrawState(textPaint: TextPaint) {
                super.updateDrawState(textPaint)
                textPaint.isUnderlineText = false
            }
        }, spannable.getSpanStart(u), spannable.getSpanEnd(u), 0)
    }
    text = spannable
}

fun TextView.setTextColorById(idColor: Int) {
    setTextColor(ContextCompat.getColor(context, idColor))
}

fun TextView.scrollTextView() {
    ellipsize = TextUtils.TruncateAt.MARQUEE
    marqueeRepeatLimit = -1 // -1 for infinite scroll
    isSingleLine = true
    isFocusable = true
    isFocusableInTouchMode = true
    isSelected = true
}

inline fun TextView.doOnTextChanged(
    crossinline action: (
        text: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) -> Unit
): TextWatcher = addTextChangedListener(onTextChanged = action)