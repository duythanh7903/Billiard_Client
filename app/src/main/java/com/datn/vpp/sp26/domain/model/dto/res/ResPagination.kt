package com.datn.vpp.sp26.domain.model.dto.res

data class ResPagination(
    val total: Int? = null,
    val page: Int? = null,
    val limit: Int? = null,
    val totalPages: Int? = null
) {
}