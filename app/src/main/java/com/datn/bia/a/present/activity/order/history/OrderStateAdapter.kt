package com.datn.bia.a.present.activity.order.history

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseRecyclerViewAdapter
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.invisibleView
import com.datn.bia.a.common.base.ext.setTextColorById
import com.datn.bia.a.common.base.ext.visibleView
import com.datn.bia.a.databinding.ItemStatusBinding
import com.datn.bia.a.domain.model.domain.OrderState

class OrderStateAdapter(
    private val contextParams: Context,
    private val onStatusClick: (index: Int, item: OrderState) -> Unit
) : BaseRecyclerViewAdapter<OrderState>() {

    var indexSelect: Int = 0
        set(value) {
            val indexSelected = field
            field = value
            notifyItemChanged(indexSelected)
            notifyItemChanged(value)
        }

    override fun getItemLayout(): Int = R.layout.item_status

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<OrderState>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun setData(
        binding: ViewDataBinding,
        item: OrderState,
        layoutPosition: Int
    ) {
        if (binding is ItemStatusBinding) {
            binding.tvStatusName.apply {
                text = contextParams.getString(item.statusNameRes)
                setTextColorById(if (layoutPosition == indexSelect) R.color.primary else R.color._232323)
            }
            if (layoutPosition == indexSelect) binding.indicator.visibleView() else binding.indicator.invisibleView()
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: OrderState, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemStatusBinding) {
            binding.root.click {
                onStatusClick.invoke(layoutPosition, obj)
            }
        }
    }
}