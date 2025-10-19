package com.datn.bia.a.common.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.core.graphics.drawable.toDrawable
import androidx.viewbinding.ViewBinding
import com.datn.bia.a.R
import com.datn.bia.a.common.base.commons.LocalizationHelper

abstract class BaseDialog<VB : ViewBinding>(
    context: Context,
    themeResId: Int = R.style.BaseThemeDialog,
    private val isoCode: String = "en",
    private val isSetShowBottom: Boolean = false
) : Dialog(context, themeResId) {
    lateinit var binding: VB

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        createContentView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalizationHelper.setLocale(context = context, isoCode = isoCode)

        if (isSetShowBottom) setDialogBottom()

        initViews()
        onResizeViews()
        onClickViews()
    }

    private fun createContentView() {
        if (this::binding.isInitialized.not()) binding = inflateBinding()
        setContentView(binding.root)
    }

    abstract fun inflateBinding(): VB

    open fun initViews() {}

    open fun onResizeViews() {}

    open fun onClickViews() {}

    private fun setDialogBottom() =
        window?.run {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.BOTTOM)
        }
}