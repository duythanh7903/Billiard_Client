package com.datn.vpp.sp26.present.activity.comment.reviews

import com.datn.vpp.sp26.domain.model.entity.FeedbackEntity

data class ReviewState(
    val listComment: List<FeedbackEntity> = emptyList()
)