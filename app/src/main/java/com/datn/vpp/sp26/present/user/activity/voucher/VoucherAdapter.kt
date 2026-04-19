package com.datn.vpp.sp26.present.user.activity.voucher

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.base.BaseRecyclerViewAdapter
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.common.base.ext.formatVND
import com.datn.vpp.sp26.common.base.ext.getRemainingTime
import com.datn.vpp.sp26.databinding.ItemVoucherBinding
import com.datn.vpp.sp26.domain.model.dto.res.ResVoucherDTO

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

    @SuppressLint("SetTextI18n")
    override fun setData(
        binding: ViewDataBinding,
        item: ResVoucherDTO,
        layoutPosition: Int
    ) {
        if (binding is ItemVoucherBinding) {
            binding.tvCode.text = item.code
            binding.tvDes.text = item.description
            binding.tvExpiring.text = item.endDate?.getRemainingTime() ?: ""
            binding.tvMaxDiscount.text =
                "${binding.root.context.getString(R.string.maximum_discount)}: ${item.maxPriceDis?.formatVND()}"
            binding.tvQuantity.text =
                "${binding.root.context.getString(R.string.quantity)}: ${item.quantity ?: 0}"

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