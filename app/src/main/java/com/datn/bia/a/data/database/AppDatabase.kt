package com.datn.bia.a.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datn.bia.a.data.database.dao.CartDao
import com.datn.bia.a.data.database.dao.CategoryDao
import com.datn.bia.a.data.database.dao.CommentDao
import com.datn.bia.a.data.database.dao.FavoriteDao
import com.datn.bia.a.data.database.dao.FeedBackDao
import com.datn.bia.a.data.database.dao.ProductDao
import com.datn.bia.a.domain.model.entity.CartEntity
import com.datn.bia.a.domain.model.entity.CategoryEntity
import com.datn.bia.a.domain.model.entity.CommentEntity
import com.datn.bia.a.domain.model.entity.FavoriteEntity
import com.datn.bia.a.domain.model.entity.FeedbackEntity
import com.datn.bia.a.domain.model.entity.ProductEntity

@Database(
    entities = [
        CartEntity::class,
        FavoriteEntity::class,
        CommentEntity::class,

        ProductEntity::class,
        CategoryEntity::class,
        FeedbackEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun commentDao(): CommentDao
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun feedbackDao(): FeedBackDao
}