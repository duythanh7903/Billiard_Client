package com.datn.vpp.sp26.present.user.activity.prod

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.base.BaseRecyclerViewAdapter
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.toValidUrl
import com.datn.vpp.sp26.databinding.ItemImageProductBinding

class ImageAdapter(
    private val contextParams: Context,
    private val onItemClicked: (String, Int) -> Unit
): BaseRecyclerViewAdapter<String>() {

    var indexSelect: Int = -1
        set(value) {
            val indexSelected = field
            field = value
            notifyItemChanged(indexSelected)
            notifyItemChanged(field)
        }

    override fun getItemLayout(): Int = R.layout.item_image_product

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<String>) {
        list.apply {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    override fun setData(
        binding: ViewDataBinding,
        item: String,
        layoutPosition: Int
    ) {
        if (binding is ItemImageProductBinding) {
            Glide.with(contextParams).load(item.toValidUrl()).into(binding.imgProduct)

            binding.container.isActivated = layoutPosition == indexSelect
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: String, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemImageProductBinding) {
            binding.root.click {
                onItemClicked.invoke(obj, layoutPosition)
            }
        }
    }
}