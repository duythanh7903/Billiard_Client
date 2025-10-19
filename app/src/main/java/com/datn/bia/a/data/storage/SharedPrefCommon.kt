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

    var isUserGlobal: Boolean
        get() = getValue("isUserGlobal", false)
        set(value) = setValue("isUserGlobal", value)

    var isConfirmConsent: Boolean
        get() = getValue("isConfirmConsent", false)
        set(value) = setValue("isConfirmConsent", value)

    var isSoundAnimationEnable: Boolean
        get() = getValue("isSoundAnimationEnable", false)
        set(value) = setValue("isSoundAnimationEnable", value)

    var isNumberRowEnable: Boolean
        get() = getValue("isNumberRowEnable", false)
        set(value) = setValue("isNumberRowEnable", value)

    var isPopupsOnPressEnable: Boolean
        get() = getValue("isPopupsOnPressEnable", false)
        set(value) = setValue("isPopupsOnPressEnable", value)

    var isFirstInstall: Boolean
        get() = getValue("isFirstInstall", true)
        set(value) = setValue("isFirstInstall", value)

    var pathCacheFont: String
        get() = getValue("pathCacheFont", "")
        set(value) = setValue("pathCacheFont", value)

    var keyboardSize: Int
        get() = getValue("keyboardSize", 10)
        set(value) = setValue("keyboardSize", value)

    var fontSize: Int
        get() = getValue("fontSize", 20)
        set(value) = setValue("fontSize", value)

    var pathCacheSound: String
        get() = getValue("pathCacheSound", "")
        set(value) = setValue("pathCacheSound", value)

    var isKeyPressEnable: Boolean
        get() = getValue("isKeyPressEnable", false)
        set(value) = setValue("isKeyPressEnable", value)

    var isSoundKeyboardEnable: Boolean
        get() = getValue("isSoundKeyboardEnable", false)
        set(value) = setValue("isSoundKeyboardEnable", value)
}