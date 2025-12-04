package com.datn.bia.a.domain.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comment_Entity")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val orderId: String = "",
    val userId: String = "",
    val star: Int = 0,
) {
}