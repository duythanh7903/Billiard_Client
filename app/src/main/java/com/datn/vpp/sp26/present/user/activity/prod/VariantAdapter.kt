package com.datn.vpp.sp26.present.user.activity.prod

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.base.BaseRecyclerViewAdapter
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.setTextColorById
import com.datn.vpp.sp26.databinding.ItemVariantBinding
import com.datn.vpp.sp26.domain.model.dto.res.ResVariantDTO

class VariantAdapter(
    private val onClick: (index: Int, item: ResVariantDTO) -> Unit
) : BaseRecyclerViewAdapter<ResVariantDTO>() {

    var indexSelect: Int = 0
        set(value) {
            val index = field
            field = value
            notifyItemChanged(index)
            notifyItemChanged(value)
        }

    override fun getItemLayout(): Int = R.layout.item_variant

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<ResVariantDTO>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun setData(
        binding: ViewDataBinding,
        item: ResVariantDTO,
        layoutPosition: Int
    ) {
        if (binding is ItemVariantBinding) {
            binding.tvVariant.text = item.color ?: ""
            binding.tvVariant.isActivated = layoutPosition == indexSelect
            binding.tvVariant.setTextColorById(if (layoutPosition == indexSelect) R.color.white else R.color._959595)
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: ResVariantDTO, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemVariantBinding) {
            binding.root.click {
                onClick.invoke(layoutPosition, obj)
            }
        }
    }
}