package com.datn.bia.a.di

import com.datn.bia.a.data.database.repository.CartRepositoryImpl
import com.datn.bia.a.data.database.repository.FavoriteRepositoryImpl
import com.datn.bia.a.data.network.repository.AuthRepositoryImpl
import com.datn.bia.a.data.network.repository.CatRepositoryImpl
import com.datn.bia.a.data.network.repository.OrderRepositoryImpl
import com.datn.bia.a.data.network.repository.ProductRepositoryImpl
import com.datn.bia.a.data.network.repository.VoucherRepositoryImpl
import com.datn.bia.a.domain.repository.AuthRepository
import com.datn.bia.a.domain.repository.CartRepository
import com.datn.bia.a.domain.repository.CatRepository
import com.datn.bia.a.domain.repository.FavoriteRepository
import com.datn.bia.a.domain.repository.OrderRepository
import com.datn.bia.a.domain.repository.ProductRepository
import com.datn.bia.a.domain.repository.VoucherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Singleton
    @Binds
    abstract fun bindCatRepository(
        impl: CatRepositoryImpl
    ): CatRepository

    @Singleton
    @Binds
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository

    @Singleton
    @Binds
    abstract fun bindVoucherRepository(
        impl: VoucherRepositoryImpl
    ): VoucherRepository

    @Singleton
    @Binds
    abstract fun bindFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository

    @Singleton
    @Binds
    abstract fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository
}