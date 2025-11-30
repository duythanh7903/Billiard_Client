@file:Suppress("UNCHECKED_CAST")
package com.datn.bia.a.data.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPrefCommon {
    private const val PREFERENCES_NAME = "Duylt_FontkeyboardTheme"
    private var sharePref: SharedPreferences? = null

    fun init(context: Context) {
        if (sharePref == null) {
            sharePref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        }
    }

    fun setValue(keyName: String, value: Any?) {
        sharePref?.edit {
            when (value) {
                is Int -> this.putInt(keyName, value)
                is Float -> this.putFloat(keyName, value)
                is Long -> this.putLong(keyName, value)
                is Boolean -> this.putBoolean(keyName, value)
                is String -> this.putString(keyName, value)
            }
        }
    }

    fun <T> getValue(keyName: String, defaultValue: T): T = when (defaultValue) {
        is Int -> (sharePref?.getInt(keyName, defaultValue) ?: defaultValue) as T
        is Long -> (sharePref?.getLong(keyName, defaultValue) ?: defaultValue) as T
        is Float -> (sharePref?.getFloat(keyName, defaultValue) ?: defaultValue) as T
        is Boolean -> (sharePref?.getBoolean(keyName, defaultValue) ?: defaultValue) as T
        is String -> (sharePref?.getString(keyName, defaultValue) ?: defaultValue) as T
        else -> defaultValue
    }

    var languageCode: String
        get() = getValue("languageCode", "en")
        set(value) = setValue("languageCode", value)

    var jsonAcc: String
        get() = getValue("jsonAcc", "")
        set(value) = setValue("jsonAcc", value)

    var isFirstInstall: Boolean
        get() = getValue("isFirstInstall", true)
        set(value) = setValue("isFirstInstall", value)
}