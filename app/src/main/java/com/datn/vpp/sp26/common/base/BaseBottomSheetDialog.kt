package com.datn.vpp.sp26.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.datn.vpp.sp26.R

abstract class BaseBottomSheetDialog<VB : ViewBinding>(
    context: Context,
    themeResId: Int = R.style.BaseThemeBottomSheetDialog
) :
    BottomSheetDialog(context, themeResId) {
    lateinit var binding: VB

    init {
        createContentView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        onResizeViews()
        onClickViews()
    }

    private fun createContentView() {
        if (this::binding.isInitialized.not()) binding = inflateBinding(layoutInflater)
        setContentView(binding.root)
    }

    abstract fun inflateBinding(layoutInflater: LayoutInflater): VB

    open fun initViews() {}

    open fun onResizeViews() {}

    open fun onClickViews() {}
}