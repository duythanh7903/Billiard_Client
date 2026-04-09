package com.datn.vpp.sp26.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datn.vpp.sp26.data.database.dao.CartDao
import com.datn.vpp.sp26.data.database.dao.CategoryDao
import com.datn.vpp.sp26.data.database.dao.CommentDao
import com.datn.vpp.sp26.data.database.dao.FavoriteDao
import com.datn.vpp.sp26.data.database.dao.FeedBackDao
import com.datn.vpp.sp26.data.database.dao.OrderDao
import com.datn.vpp.sp26.data.database.dao.ProductDao
import com.datn.vpp.sp26.domain.model.entity.CartEntity
import com.datn.vpp.sp26.domain.model.entity.CategoryEntity
import com.datn.vpp.sp26.domain.model.entity.CommentEntity
import com.datn.vpp.sp26.domain.model.entity.FavoriteEntity
import com.datn.vpp.sp26.domain.model.entity.FeedbackEntity
import com.datn.vpp.sp26.domain.model.entity.OrderEntity
import com.datn.vpp.sp26.domain.model.entity.ProductEntity

@Database(
    entities = [
        CartEntity::class,
        FavoriteEntity::class,
        CommentEntity::class,

        ProductEntity::class,
        CategoryEntity::class,
        FeedbackEntity::class,
        OrderEntity::class
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
    abstract fun orderDao(): OrderDao
}