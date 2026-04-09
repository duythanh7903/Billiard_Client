package com.datn.vpp.sp26.di

import com.datn.vpp.sp26.data.database.repository.CartRepositoryImpl
import com.datn.vpp.sp26.data.database.repository.CategoryCacheRepositoryImpl
import com.datn.vpp.sp26.data.database.repository.CommentCacheRepositoryImpl
import com.datn.vpp.sp26.data.database.repository.FavoriteRepositoryImpl
import com.datn.vpp.sp26.data.database.repository.FeedbackRepositoryImpl
import com.datn.vpp.sp26.data.database.repository.OrderCacheRepositoryImpl
import com.datn.vpp.sp26.data.database.repository.ProductCacheRepositoryImpl
import com.datn.vpp.sp26.data.network.repository.AuthRepositoryImpl
import com.datn.vpp.sp26.data.network.repository.CatRepositoryImpl
import com.datn.vpp.sp26.data.network.repository.CommentRepositoryImpl
import com.datn.vpp.sp26.data.network.repository.OrderRepositoryImpl
import com.datn.vpp.sp26.data.network.repository.ProductRepositoryImpl
import com.datn.vpp.sp26.data.network.repository.VoucherRepositoryImpl
import com.datn.vpp.sp26.domain.repository.AuthRepository
import com.datn.vpp.sp26.domain.repository.CartRepository
import com.datn.vpp.sp26.domain.repository.CatRepository
import com.datn.vpp.sp26.domain.repository.CategoryCacheRepository
import com.datn.vpp.sp26.domain.repository.CommentCacheRepository
import com.datn.vpp.sp26.domain.repository.CommentRepository
import com.datn.vpp.sp26.domain.repository.FavoriteRepository
import com.datn.vpp.sp26.domain.repository.FeedbackRepository
import com.datn.vpp.sp26.domain.repository.OrderCacheRepository
import com.datn.vpp.sp26.domain.repository.OrderRepository
import com.datn.vpp.sp26.domain.repository.ProductCacheRepository
import com.datn.vpp.sp26.domain.repository.ProductRepository
import com.datn.vpp.sp26.domain.repository.VoucherRepository
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

    @Singleton
    @Binds
    abstract fun bindCommentCacheRepository(
        impl: CommentCacheRepositoryImpl
    ): CommentCacheRepository

    @Singleton
    @Binds
    abstract fun bindCommentRepository(
        impl: CommentRepositoryImpl
    ): CommentRepository

    @Singleton
    @Binds
    abstract fun bindProductCacheRepository(
        impl: ProductCacheRepositoryImpl
    ): ProductCacheRepository

    @Singleton
    @Binds
    abstract fun bindCategoryCacheRepository(
        impl: CategoryCacheRepositoryImpl
    ): CategoryCacheRepository

    @Singleton
    @Binds
    abstract fun bindFeedbackRepository(
        impl: FeedbackRepositoryImpl
    ): FeedbackRepository

    @Singleton
    @Binds
    abstract fun bindOrderCacheRepository(
        impl: OrderCacheRepositoryImpl
    ): OrderCacheRepository

}