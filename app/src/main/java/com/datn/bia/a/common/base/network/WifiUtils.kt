package com.datn.bia.a.common.base.network

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.WIFI_SERVICE

object WifiUtils {
    fun showDialogWifiSystem(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            openWifiLargerVersionQ(activity)
        } else {
            openWifiSmallerVersionQ(activity)
        }
    }

    private fun openWifiLargerVersionQ(activity: AppCompatActivity) =
        try {
            activity.startActivity(
                Intent("android.settings.panel.action.WIFI").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    private fun openWifiSmallerVersionQ(activity: AppCompatActivity) {
        val wifiManager = activity.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        try {
            wifiManager.isWifiEnabled = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}