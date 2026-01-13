package com.datn.bia.a.domain.model.entity

import android.renderscript.Double3
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.datn.bia.a.domain.model.dto.res.ResVariantDTO

@Entity(tableName = "ProductEntity")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val albumImage: List<String>,
    val des: String,
    val status: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val discount: Int,

    val idCateGory: String,
    val categoryName: String,

    val variants: List<ResVariantDTO>
) {
}