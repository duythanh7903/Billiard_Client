package com.datn.bia.a.present.activity.voucher

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseRecyclerViewAdapter
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.getRemainingTime
import com.datn.bia.a.databinding.ItemVoucherBinding
import com.datn.bia.a.domain.model.dto.res.ResVoucherDTO

class VoucherAdapter(
    private val onSelected: (index: Int, voucher: ResVoucherDTO) -> Unit
) : BaseRecyclerViewAdapter<ResVoucherDTO>() {

    var indexSelect = -1
        set(value) {
            val indexSelected = field
            field = value
            notifyItemChanged(indexSelected)
            notifyItemChanged(value)
        }

    override fun getItemLayout(): Int = R.layout.item_voucher

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<ResVoucherDTO>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun setData(
        binding: ViewDataBinding,
        item: ResVoucherDTO,
        layoutPosition: Int
    ) {
        if (binding is ItemVoucherBinding) {
            binding.tvCode.text = item.code
            binding.tvDes.text = item.description
            binding.tvExpiring.text = item.endDate?.getRemainingTime() ?: ""

            binding.chb.isActivated = (layoutPosition == indexSelect)
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: ResVoucherDTO, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemVoucherBinding) {
            binding.root.click {
                onSelected.invoke(layoutPosition, obj)
            }
        }
    }

    fun getVoucherSelect(): ResVoucherDTO? =
        if (indexSelect == -1) null else list[indexSelect]
}