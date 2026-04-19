package com.datn.vpp.sp26.present.user.activity.comment.reviews

import com.datn.vpp.sp26.domain.model.entity.FeedbackEntity

data class ReviewState(
    val listComment: List<FeedbackEntity> = emptyList()
)