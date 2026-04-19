package com.datn.vpp.sp26.present.user.activity.comment.reviews

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.base.BaseRecyclerViewAdapter
import com.datn.vpp.sp26.databinding.ItemReviewBinding
import com.datn.vpp.sp26.domain.model.dto.res.ResCommentDTO

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