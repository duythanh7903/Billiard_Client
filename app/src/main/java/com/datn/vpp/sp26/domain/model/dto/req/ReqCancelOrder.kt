package com.datn.vpp.sp26.domain.model.dto.req

data class ReqCancelOrder(
    val cancelReason: String = "",
    val status: String = ""
) {
}