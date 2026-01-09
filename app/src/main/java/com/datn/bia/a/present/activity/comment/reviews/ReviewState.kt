package com.datn.bia.a.present.activity.comment.reviews

import com.datn.bia.a.domain.model.entity.FeedbackEntity

data class ReviewState(
    val listComment: List<FeedbackEntity> = emptyList()
)