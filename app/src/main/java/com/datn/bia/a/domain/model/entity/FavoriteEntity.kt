package com.datn.bia.a.domain.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorite_Entity")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val productId: String,
) {
}