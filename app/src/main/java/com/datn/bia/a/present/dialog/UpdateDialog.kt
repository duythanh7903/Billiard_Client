package com.datn.bia.a.present.dialog

import android.content.Context
import com.datn.bia.a.common.base.BaseDialog
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.DialogUpdateBinding

class UpdateDialog(
    contextParams: Context,
    private val title: String,
    private val onUpdate: (String) -> Unit,
    private val onClose: () -> Unit
): BaseDialog<DialogUpdateBinding>(contextParams, isSetShowBottom = true) {
    override fun inflateBinding(): DialogUpdateBinding =
        DialogUpdateBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()

        setCancelable(false)
        binding.tvTitle.text = title
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.icClose.click {
            onClose.invoke()
            binding.edtMessage.text.clear()
            dismiss()
        }

        binding.btnSend.click {
            onUpdate.invoke(binding.edtMessage.text.toString().trim())
            dismiss()
        }
    }
}