package com.datn.bia.a.present.activity.comment.reviews

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.datn.bia.a.R
import com.datn.bia.a.common.base.BaseRecyclerViewAdapter
import com.datn.bia.a.databinding.ItemReviewBinding
import com.datn.bia.a.domain.model.dto.res.ResCommentDTO

class ReviewAdapter: BaseRecyclerViewAdapter<ResCommentDTO>() {
    override fun getItemLayout(): Int = R.layout.item_review

    @SuppressLint("NotifyDataSetChanged")
    override fun submitData(newData: List<ResCommentDTO>) {
        list.apply {
            clear()
            addAll(newData)
            notifyDataSetChanged()
        }
    }

    override fun setData(
        binding: ViewDataBinding,
        item: ResCommentDTO,
        layoutPosition: Int
    ) {
        if (binding is ItemReviewBinding) {
            binding.tvIdUser.text = item.userId?.email
            binding.tvStars.text = item.rating?.toString() ?: 0.toString()
            binding.tvComment.text = item.content
        }
    }
}