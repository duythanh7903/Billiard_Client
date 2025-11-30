package com.datn.bia.a.di

import android.content.Context
import androidx.room.Room
import com.datn.bia.a.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()

    @Singleton
    @Provides
    fun provideCartDao(db: AppDatabase) = db.cartDao()

    @Singleton
    @Provides
    fun provideFavoriteDao(db: AppDatabase) = db.favoriteDao()
}