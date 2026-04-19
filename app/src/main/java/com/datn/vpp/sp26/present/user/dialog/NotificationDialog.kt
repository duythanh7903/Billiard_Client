package com.datn.vpp.sp26.present.user.dialog

import android.content.Context
import com.datn.vpp.sp26.common.base.BaseDialog
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.databinding.DialogNotificationBinding

class NotificationDialog(
    private val context: Context,
    private val onOk: () -> Unit
) : BaseDialog<DialogNotificationBinding>(context) {
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