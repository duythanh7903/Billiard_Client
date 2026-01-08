package com.datn.bia.a.domain.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductEntity")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val albumImage: List<String>,
    val des: String,
    val status: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val discount: Int,

    val idCateGory: String,
    val categoryName: String,
) {
}