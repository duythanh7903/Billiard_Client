package com.datn.vpp.sp26.present.user.dialog

import android.content.Context
import com.datn.vpp.sp26.common.base.BaseDialog
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.databinding.DialogConfirmCancelOrderBinding

class ConfirmCancelOrderDialog(
    private val contextParams: Context,
    private val onNo: () -> Unit,
    private val onYes: () -> Unit,
): BaseDialog<DialogConfirmCancelOrderBinding>(contextParams, isSetShowBottom = true) {
    override fun inflateBinding(): DialogConfirmCancelOrderBinding =
        DialogConfirmCancelOrderBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()

        setCancelable(false)
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.btnRate.click {
            onYes.invoke()
            dismiss()
        }

        binding.btnLater.click {
            onNo.invoke()
            dismiss()
        }
    }
}