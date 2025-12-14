package com.datn.bia.a.domain.model.dto.res

data class ResCommentDTO(
    val _id: String? = null,
    val userId: ResUserIdCommentDTO? = null,
    val productId: ResProductIdCommentDTO? = null,
    val content: String? = null,
    val rating: Int? = null,
    val __v: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
}

data class ResUserIdCommentDTO(
    val _id: String? = null,
    val email: String? = null
)

data class ResProductIdCommentDTO(
    val _id: String? = null,
    val name: String? = null,
    val price: Int? = null,
)