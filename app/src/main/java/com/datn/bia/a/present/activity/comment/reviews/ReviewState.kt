package com.datn.bia.a.present.activity.comment.reviews

import com.datn.bia.a.common.UiState
import com.datn.bia.a.domain.model.dto.res.ResCommentDTO

data class ReviewState(
    val uiState: UiState<List<ResCommentDTO>> = UiState.Idle
){
}