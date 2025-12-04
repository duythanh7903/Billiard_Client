package com.datn.bia.a.domain.model.domain

import com.datn.bia.a.R

data class OrderState(
    val id: Long = 0,
    val statusNameRes: Int = R.string.to_pay,
) {
    companion object {
        fun getListStatus() = listOf(
            OrderState(id = 0L, statusNameRes = R.string.to_pay),
            OrderState(id = 1L, statusNameRes = R.string.to_receive),
            OrderState(id = 2L, statusNameRes = R.string.completed),
            OrderState(id = 3L, statusNameRes = R.string.cancelled),
        )
    }
}