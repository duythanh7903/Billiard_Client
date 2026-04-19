package com.datn.vpp.sp26.present.user.activity.setting.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.base.BaseRecyclerViewAdapter
import com.datn.vpp.sp26.common.base.ext.click
import com.datn.vpp.sp26.databinding.ItemSettingBinding
import com.datn.vpp.sp26.domain.model.domain.SettingItem

class SettingItemAdapter(
    private val contextParams: Context,
    private val onSettingItem: (index: Int, setting: SettingItem) -> Unit
): BaseRecyclerViewAdapter<SettingItem>() {
    override fun getItemLayout(): Int = R.layout.item_setting

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<SettingItem>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun setData(
        binding: ViewDataBinding,
        item: SettingItem,
        layoutPosition: Int
    ) {
        if (binding is ItemSettingBinding) {
            binding.tvSetting.text = contextParams.getString(item.titleRes)
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: SettingItem, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemSettingBinding) {
            binding.root.click {
                onSettingItem.invoke(layoutPosition, obj)
            }
        }
    }
}