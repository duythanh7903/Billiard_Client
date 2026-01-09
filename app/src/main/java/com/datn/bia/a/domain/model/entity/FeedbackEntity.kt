package com.datn.bia.a.domain.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FeedBackEntity")
data class FeedbackEntity(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val content: String,
    val rating: Int,
    val __v: Int,
    val createdAt: String,
    val updatedAt: String,

    val userId: String,
    val email: String,

    val idProduct: String,
    val productName: String,
    val productPrice: Int,
) {
}