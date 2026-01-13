package com.datn.bia.a.present.activity.order.confirm

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseRecyclerViewAdapter
import com.datn.bia.a.common.base.ext.formatVND
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.databinding.ItemProdOrderBinding
import com.datn.bia.a.domain.model.domain.Cart

class CartConfirmAdapter(
    private val contextParams: Context
) : BaseRecyclerViewAdapter<Cart>() {
    override fun getItemLayout(): Int = R.layout.item_prod_order

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<Cart>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setData(
        binding: ViewDataBinding,
        item: Cart,
        layoutPosition: Int
    ) {
        if (binding is ItemProdOrderBinding) {
            Glide.with(contextParams).load(item.productImage).into(binding.imgProduct)
            binding.tvProductName.text = item.productName
            binding.tvCountProduct.text = "x${item.productQuantity}"
            if (item.productDiscount > 0) {
                binding.tvBeforeDiscount.apply {
                    text = item.productPrice.formatVND()
                    visibleView()
                }
                binding.viewLine.visibleView()
                binding.tvPriceFinal.text =
                    (item.productPrice - (item.productPrice * item.productDiscount / 100)).formatVND()
            } else {
                binding.tvBeforeDiscount.goneView()
                binding.viewLine.goneView()
                binding.tvPriceFinal.text = item.productPrice.formatVND()
            }

            binding.tvColor.text = contextParams.getString(R.string.color_, item.variant.color ?: "")
        }
    }
}