package com.datn.vpp.sp26.present.user.activity.order.history.tab

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.base.BaseRecyclerViewAdapter
import com.datn.vpp.sp26.common.base.ext.formatVND
import com.datn.vpp.sp26.common.base.ext.goneView
import com.datn.vpp.sp26.common.base.ext.toValidUrl
import com.datn.vpp.sp26.common.base.ext.visibleView
import com.datn.vpp.sp26.databinding.ItemProdOrderBinding
import com.datn.vpp.sp26.domain.model.dto.res.OrderProduct

class ProdAdapter(
    private val contextParams: Context
) : BaseRecyclerViewAdapter<OrderProduct>() {
    override fun getItemLayout(): Int = R.layout.item_prod_order

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<OrderProduct>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setData(
        binding: ViewDataBinding,
        item: OrderProduct,
        layoutPosition: Int
    ) {
        if (binding is ItemProdOrderBinding) {
            val priceAfterDis = item.priceAfterDis ?: 0.0
            val priceBeforeDis = item.priceBeforeDis ?: 0.0

            binding.tvProductName.text = item.name ?: ""

            Glide.with(contextParams).load(item.productId?.imageUrl?.toValidUrl())
                .into(binding.imgProduct)
            binding.tvCountProduct.text = "x${item.quantity}"
            if (priceAfterDis == priceBeforeDis) {
                binding.tvPriceFinal.text = priceAfterDis.formatVND()
                binding.tvBeforeDiscount.goneView()
                binding.viewLine.goneView()
            } else {
                binding.tvPriceFinal.text = priceBeforeDis.formatVND()
                binding.tvBeforeDiscount.apply {
                    text = priceAfterDis.formatVND()
                    visibleView()
                }
                binding.viewLine.visibleView()
            }
            binding.tvColor.text = contextParams.getString(R.string.color_, item.color ?: "")
        }
    }
}