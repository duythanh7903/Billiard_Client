package com.datn.bia.a.common.base.commons

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocalizationHelper {

    fun setLocale(context: Context, isoCode: String) {
        val locale =
            if (isoCode.isNotBlank() || isoCode.isEmpty()) Locale(isoCode)
            else Locale.getDefault()
        Locale.setDefault(locale)
        val config = Configuration().apply { this.locale = locale }
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }


}