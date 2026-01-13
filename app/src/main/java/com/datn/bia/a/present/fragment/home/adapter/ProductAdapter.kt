package com.datn.bia.a.present.fragment.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseRecyclerViewAdapter
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.formatVND
import com.datn.bia.a.common.base.ext.goneView
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.databinding.ItemProductBinding
import com.datn.bia.a.domain.model.dto.res.ResProductDataDTO

class ProductAdapter(
    private val contextParams: Context,
    private val onProductClick: (Int, ResProductDataDTO) -> Unit
) : BaseRecyclerViewAdapter<ResProductDataDTO>() {
    override fun getItemLayout(): Int = R.layout.item_product

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<ResProductDataDTO>) {
        list.clear()
        list.addAll(newData)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun setData(
        binding: ViewDataBinding,
        item: ResProductDataDTO,
        layoutPosition: Int
    ) {
        if (binding is ItemProductBinding) {
            Glide.with(contextParams).load(item.imageUrl).into(binding.imgProduct)
            val price = item.variants?.firstOrNull()?.price ?: 0.0

            binding.tvProductName.text = item.name
            binding.tvPrice.text = if (item.discount != null && item.discount > 0) {
                (price - (price * item.discount / 100)).formatVND()
            } else price.formatVND()
            if (item.discount == null || item.discount == 0) {
                binding.tvDiscount.goneView()
            } else {
                binding.tvDiscount.apply {
                    visibleView()
                    text = "-${item.discount}%"
                }
            }
        }
    }

    override fun onClickViews(
        binding: ViewDataBinding,
        obj: ResProductDataDTO,
        layoutPosition: Int
    ) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemProductBinding) {
            binding.root.click {
                onProductClick.invoke(layoutPosition, obj)
            }
        }
    }
}