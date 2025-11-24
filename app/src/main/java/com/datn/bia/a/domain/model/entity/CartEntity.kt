package com.datn.bia.a.domain.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartEntity")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val idProd: String = "",
    val idUser: String = "",
    val quantity: Int = 0,
    val isEnable: Boolean = false,
) {
}