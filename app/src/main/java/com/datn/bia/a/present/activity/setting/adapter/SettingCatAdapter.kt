package com.datn.bia.a.present.activity.setting.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseRecyclerViewAdapter
import com.datn.bia.a.databinding.ItemSettingCatBinding
import com.datn.bia.a.domain.model.domain.SettingCat
import com.datn.bia.a.domain.model.domain.SettingItem

class SettingCatAdapter(
    private val contextParams: Context,
    private val onSettingItem: (Int, SettingItem) -> Unit
): BaseRecyclerViewAdapter<SettingCat>() {
    override fun getItemLayout(): Int = R.layout.item_setting_cat

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<SettingCat>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun setData(
        binding: ViewDataBinding,
        item: SettingCat,
        layoutPosition: Int
    ) {
        if (binding is ItemSettingCatBinding) {
            binding.tvTitleSetting.text = contextParams.getString(item.titleRes)
            val settingItemAdapter = SettingItemAdapter(
                contextParams = contextParams,
                onSettingItem = onSettingItem
            ).apply {
                submitData(item.items)
            }
            binding.rcvSetting.adapter = settingItemAdapter/*.apply {
                submitData(
                    when (item.type) {
                        TypeSettingItem.MY_ACCOUNT -> SettingItem.getSettingItemTypeAccount()
                        TypeSettingItem.SETTING -> SettingItem.getSettingItemTypeSetting()
                        else -> SettingItem.getSettingItemTypeHelp()
                    }
                )
            }*/
        }
    }
}