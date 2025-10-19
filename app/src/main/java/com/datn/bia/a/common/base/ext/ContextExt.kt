package com.datn.bia.a.common.base.ext

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.datn.bia.a.R

/*fun Context.isNetwork(activity: Context): Boolean {
    var haveConnectedWifi = false
    var haveConnectedMobile = false
    val cm =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.allNetworkInfo
    for (ni in netInfo) {
        if (ni.typeName.equals("WIFI", ignoreCase = true))
            if (ni.isConnected) haveConnectedWifi = true
        if (ni.typeName.equals("MOBILE", ignoreCase = true))
            if (ni.isConnected) haveConnectedMobile = true
    }
    return haveConnectedWifi || haveConnectedMobile
}*/

fun Context.getCurrentSdkVersion(): Int = Build.VERSION.SDK_INT

private var lastToastTime = 0L
private var lastToast: Toast? = null
private const val TOAST_INTERVAL = 1500L // 1,5 giây
fun Context.showToastOnce(message: String, duration: Int = Toast.LENGTH_SHORT) {
    val now = System.currentTimeMillis()
    if (now - lastToastTime < TOAST_INTERVAL) return

    lastToast?.cancel()
    lastToast = Toast.makeText(this, message, duration).apply { show() }
    lastToastTime = now
}

fun Context.getWidthScreenPx(): Int =
    resources.displayMetrics.widthPixels

fun Context.getHeightScreenPx(): Int =
    resources.displayMetrics.heightPixels

fun Context.getWidthScreenDp(): Float =
    resources.displayMetrics.widthPixels.pxToDp(this)

fun Context.getHeightScreenDp(): Float =
    resources.displayMetrics.widthPixels.dpToPx(this)

