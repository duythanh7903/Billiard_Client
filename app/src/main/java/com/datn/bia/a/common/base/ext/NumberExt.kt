package com.datn.bia.a.common.base.ext

import android.content.Context
import android.util.DisplayMetrics
import java.text.NumberFormat
import java.util.Locale

fun Number.dpToPx(context: Context): Float {
    return this.toFloat() * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Number.pxToDp(context: Context): Float {
    return this.toFloat() / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Number.formatVND(): String {
    val localeVN = Locale("vi", "VN")
    val formatter = NumberFormat.getCurrencyInstance(localeVN)
    return formatter.format(this)
}