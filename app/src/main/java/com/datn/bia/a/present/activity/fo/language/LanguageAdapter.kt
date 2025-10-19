package com.datn.bia.a.present.activity.fo.language

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseRecyclerViewAdapter
import com.datn.bia.a.common.base.ext.click
import com.datn.bia.a.databinding.ItemLanguageBinding
import com.datn.bia.a.model.domain.Language

class LanguageAdapter(
    private val context: Context,
    private val onLanguageClicked: (index: Int, language: Language) -> Unit
): BaseRecyclerViewAdapter<Language>() {
    var indexSelect: Int = 0
        set(value) {
            val indexSelectedBefore = field
            field = value
            notifyItemChanged(value)
            notifyItemChanged(indexSelectedBefore)
        }

    override fun getItemLayout(): Int = R.layout.item_language

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<Language>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun setData(
        binding: ViewDataBinding,
        item: Language,
        layoutPosition: Int
    ) {
        if (binding is ItemLanguageBinding) {
            Glide.with(context).load(item.image).into(binding.icCountriesFlag)
            binding.tvLanguageName.text = item.languageName
            binding.icRd.isActivated = layoutPosition == indexSelect
        }
    }

    override fun onClickViews(binding: ViewDataBinding, obj: Language, layoutPosition: Int) {
        super.onClickViews(binding, obj, layoutPosition)

        if (binding is ItemLanguageBinding) {
            binding.root.click {
                onLanguageClicked.invoke(layoutPosition, obj)
            }
        }
    }

    fun getIsoLanguageCurrent() =
        list[indexSelect].isoLanguage
}