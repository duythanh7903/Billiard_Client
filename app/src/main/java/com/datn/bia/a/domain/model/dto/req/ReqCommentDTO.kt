package com.datn.bia.a.domain.model.dto.req

data class ReqCommentDTO(
    val userId: String = "",
    val productId: List<String> = emptyList(),
    val content: String = "",
    val rating: Int = 0
){
}