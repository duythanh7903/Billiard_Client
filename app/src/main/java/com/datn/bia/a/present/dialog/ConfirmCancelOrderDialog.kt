package com.datn.bia.a.present.dialog

import android.content.Context
import com.datn.bia.a.common.base.BaseDialog
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.DialogConfirmCancelOrderBinding

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