package com.datn.bia.a.present.dialog

import android.content.Context
import com.datn.bia.a.common.base.BaseDialog
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.DialogMessageBinding

class MessageDialog(
    contextParam: Context,
    private val onSend: (String) -> Unit
) : BaseDialog<DialogMessageBinding>(
    context = contextParam,
    isSetShowBottom = true
) {
    override fun inflateBinding(): DialogMessageBinding =
        DialogMessageBinding.inflate(layoutInflater)

    override fun onClickViews() {
        super.onClickViews()

        binding.icClose.click {
            dismiss()
            binding.edtMessage.text.clear()
        }

        binding.btnSend.click {
            onSend.invoke(binding.edtMessage.text.toString().trim())
        }
    }
}