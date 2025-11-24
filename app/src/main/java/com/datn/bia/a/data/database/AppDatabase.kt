package com.datn.bia.a.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.datn.bia.a.data.database.dao.CartDao
import com.datn.bia.a.domain.model.entity.CartEntity

@Database(
    entities = [CartEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cartDao(): CartDao
}