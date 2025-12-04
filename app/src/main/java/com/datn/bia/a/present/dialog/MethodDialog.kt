package com.datn.bia.a.present.dialog

import android.content.Context
import com.datn.bia.a.common.MethodPayment
import com.datn.bia.a.common.base.BaseDialog
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.DialogMethodBinding

class MethodDialog(
    context: Context,
    private val onConfirmMethodPayment: (methodPayment: MethodPayment) -> Unit
) : BaseDialog<DialogMethodBinding>(
    context = context,
    isSetShowBottom = true
) {

    private var methodPayment: MethodPayment = MethodPayment.CASH_ON_DELIVERY

    override fun inflateBinding(): DialogMethodBinding =
        DialogMethodBinding.inflate(layoutInflater)

    override fun initViews() {
        super.initViews()

        onMethodPayment()
    }

    override fun onClickViews() {
        super.onClickViews()

        binding.btnCash.click {
            methodPayment = MethodPayment.CASH_ON_DELIVERY
            onMethodPayment()
        }

        binding.btnGg.click {
            methodPayment = MethodPayment.GOOGLE_PAY
            onMethodPayment()
        }

        binding.btnZalo.click {
            methodPayment = MethodPayment.ZALO_PAY
            onMethodPayment()
        }

        binding.btnVn.click {
            methodPayment = MethodPayment.VN_PAY
            onMethodPayment()
        }

        binding.btnOk.click {
            onConfirmMethodPayment.invoke(methodPayment)
            cancel()
        }
    }

    private fun onMethodPayment() {
        when (methodPayment) {
            MethodPayment.CASH_ON_DELIVERY -> {
                binding.chbCashOnDelivery.isActivated = true
                binding.chbGooglePay.isActivated = false
                binding.chbZaloPay.isActivated = false
                binding.chbVnPay.isActivated = false
            }
            MethodPayment.GOOGLE_PAY -> {
                binding.chbCashOnDelivery.isActivated = false
                binding.chbGooglePay.isActivated = true
                binding.chbZaloPay.isActivated = false
                binding.chbVnPay.isActivated = false
            }
            MethodPayment.ZALO_PAY -> {
                binding.chbCashOnDelivery.isActivated = false
                binding.chbGooglePay.isActivated = false
                binding.chbZaloPay.isActivated = true
                binding.chbVnPay.isActivated = false
            }
            MethodPayment.VN_PAY -> {
                binding.chbCashOnDelivery.isActivated = false
                binding.chbGooglePay.isActivated = false
                binding.chbZaloPay.isActivated = false
                binding.chbVnPay.isActivated = true
            }
        }
    }
}