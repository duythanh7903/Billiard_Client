package com.datn.vpp.sp26.present.dialog

import android.content.Context
import com.datn.vpp.sp26.common.base.BaseDialog
import com.datn.vpp.sp26.databinding.DialogLoadingBinding

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