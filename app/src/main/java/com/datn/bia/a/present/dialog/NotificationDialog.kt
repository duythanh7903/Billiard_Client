package com.datn.bia.a.present.dialog

import android.content.Context
import com.datn.bia.a.common.base.BaseDialog
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.DialogNotificationBinding

class NotificationDialog(
    private val context: Context,
    private val onOk: () -> Unit
): BaseDialog<DialogNotificationBinding>(context) {
    override fun inflateBinding(): DialogNotificationBinding =
        DialogNotificationBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()

        setCancelable(false)
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.btnOk.click {
            onOk.invoke()
            dismiss()
        }
    }
}