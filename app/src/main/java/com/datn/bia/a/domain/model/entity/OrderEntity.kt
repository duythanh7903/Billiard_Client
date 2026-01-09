package com.datn.bia.a.domain.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.datn.bia.a.domain.model.dto.res.OrderProduct

@Entity(tableName = "OrderEntity")
data class OrderEntity(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val madh: Int,
    val customerName: String,
    val totalPrice: Int,
    val phone: String,
    val address: String,
    val products: List<OrderProduct>,
    val status: String,
    val payment: String,
    val userId: String,
    val voucherId: String,
    val note: String,
    val orderDate: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int,
)