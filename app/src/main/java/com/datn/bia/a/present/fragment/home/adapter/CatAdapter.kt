package com.datn.bia.a.present.fragment.home.adapter

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseRecyclerViewAdapter
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.common.base.ext.setTextColorById
import com.datn.bia.a.databinding.ItemCategoryBinding
import com.datn.bia.a.domain.model.dto.res.ResCatDTO

class CatAdapter(
    private val onClick: (Int, ResCatDTO) -> Unit
): BaseRecyclerViewAdapter<ResCatDTO>() {

    private var indexSelect: Int = -1
        set(value) {
            val temp = field
            field = value
            notifyItemChanged(temp)
            notifyItemChanged(field)
        }

    override fun getItemLayout(): Int = R.layout.item_category

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<ResCatDTO>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun setData(
        binding: ViewDataBinding,
        item: ResCatDTO,
        layoutPosition: Int
    ) {
        if (binding is ItemCategoryBinding) {
            binding.tvCategory.apply {
                isActivated = layoutPosition == indexSelect
                text = item.name ?: context.getString(R.string.unknown)
                setTextColorById(if (isActivated) R.color.white else R.color._959595)
            }
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: ResCatDTO, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemCategoryBinding) {
            binding.tvCategory.click {
                onClick.invoke(layoutPosition, obj)
            }
        }
    }

    fun changeIndexSelect(newIndex: Int) {
        indexSelect = newIndex
    }
}