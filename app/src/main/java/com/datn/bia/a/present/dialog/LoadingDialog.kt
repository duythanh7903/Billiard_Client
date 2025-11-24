package com.datn.bia.a.present.dialog

import android.content.Context
import com.datn.bia.a.common.base.BaseDialog
import com.datn.bia.a.databinding.DialogLoadingBinding

class LoadingDialog(
    context: Context
) : BaseDialog<DialogLoadingBinding>(context) {
    override fun inflateBinding(): DialogLoadingBinding =
        DialogLoadingBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()

        setCancelable(false)
    }
}