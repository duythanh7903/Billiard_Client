package com.datn.vpp.sp26.application

import android.app.Application
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApp: Application() {
    companion object {
        var jsonSearchResult: String = ""
    }

    override fun onCreate() {
        super.onCreate()

        SharedPrefCommon.init(this)
    }
}