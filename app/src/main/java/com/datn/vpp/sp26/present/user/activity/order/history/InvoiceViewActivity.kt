package com.datn.vpp.sp26.present.user.activity.order.history

import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.common.base.BaseActivity
import com.datn.vpp.sp26.databinding.ActivityInvoiceViewBinding
import com.datn.vpp.sp26.domain.model.dto.res.ResOrderDTO
import com.google.gson.Gson

class InvoiceViewActivity : BaseActivity<ActivityInvoiceViewBinding>() {

    override fun getLayoutActivity(): Int = R.layout.activity_invoice_view

    override fun initViews() {
        super.initViews()

        val json = intent.getStringExtra(AppConst.KEY_ORDER_INVOICE) ?: run {
            finish()
            return
        }
        val order = try {
            Gson().fromJson(json, ResOrderDTO::class.java)
        } catch (_: Exception) {
            finish()
            return
        }

        binding.toolbarInvoice.setNavigationIcon(R.drawable.ic_back_black)
        binding.toolbarInvoice.setNavigationOnClickListener { finish() }

        InvoiceContentBinder.bind(this, binding.root, order)
    }
}
