package com.datn.bia.a.present.dialog

import android.content.Context
import com.datn.bia.a.common.base.BaseDialog
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.DialogConfirmCompleteBinding

class ConfirmCompleteDialog(
    private val contextParams: Context,
    private val onNo: () -> Unit,
    private val onYes: () -> Unit,
) : BaseDialog<DialogConfirmCompleteBinding>(contextParams, isSetShowBottom = true) {
    override fun inflateBinding(): DialogConfirmCompleteBinding =
        DialogConfirmCompleteBinding.inflate(layoutInflater)

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