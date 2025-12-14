package com.datn.bia.a.present.dialog

import android.content.Context
import com.datn.bia.a.common.base.BaseDialog
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.DialogMessageBinding

class ReasonCancelDialog(
    private val contextParams: Context,
    private val onMessage: (String) -> Unit
) : BaseDialog<DialogMessageBinding>(
    contextParams,
    isSetShowBottom = true
) {
    override fun inflateBinding(): DialogMessageBinding =
        DialogMessageBinding.inflate(layoutInflater)

    override fun onClickViews() {
        super.onClickViews()

        binding.btnSend.click {
            onMessage.invoke(binding.edtMessage.text.toString().trim())
            dismiss()
        }

        binding.icClose.click {
            dismiss()
        }
    }
}