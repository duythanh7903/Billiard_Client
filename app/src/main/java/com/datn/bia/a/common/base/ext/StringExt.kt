package com.datn.bia.a.common.base.ext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

/**
 * Load Drawable từ file path.
 */
@SuppressLint("UseKtx")
fun String.loadDrawableFromPath(context: Context): Drawable =
    try {
        val bitmap = BitmapFactory.decodeFile(this)
        BitmapDrawable(context.resources, bitmap)
    } catch (e: Exception) {
        e.printStackTrace();
        ColorDrawable(Color.TRANSPARENT)
    }

/**
 * Load Bitmap từ file path.
 */
@SuppressLint("UseKtx")
fun String.loadBitmapFromPath(): Bitmap =
    try {
        BitmapFactory.decodeFile(this);
    } catch (e: Exception) {
        e.printStackTrace()
        Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // fallback
    }

fun String.changeSpaceToUnderLine(): String =
    this.replace(" ", "_")

fun String.isValidEmailAndroid(): Boolean =
    this.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
