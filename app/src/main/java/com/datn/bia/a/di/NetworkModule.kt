package com.datn.bia.a.di

import com.datn.bia.a.BuildConfig
import com.datn.bia.a.data.network.factory.ResultWrapperCallAdapterFactory
import com.datn.bia.a.data.network.service.AuthService
import com.datn.bia.a.data.network.service.CatService
import com.datn.bia.a.data.network.service.CommentService
import com.datn.bia.a.data.network.service.OrderService
import com.datn.bia.a.data.network.service.ProductService
import com.datn.bia.a.data.network.service.VoucherService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(loggingInterceptor)
    } else {
        OkHttpClient.Builder()
    }.apply {
        readTimeout(30, TimeUnit.SECONDS)
        connectTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
    }.build()

    @Singleton
    @Provides
    fun provideNetworking(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl("http://10.0.2.2:8000/api/")
            .baseUrl("http://192.168.0.103:8000/api/")
            .addCallAdapterFactory(
                ResultWrapperCallAdapterFactory()
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient).build()

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ) = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideProductService(
        retrofit: Retrofit
    ) = retrofit.create(ProductService::class.java)

    @Provides
    @Singleton
    fun provideCatService(
        retrofit: Retrofit
    ) = retrofit.create(CatService::class.java)

    @Provides
    @Singleton
    fun provideVoucherService(
        retrofit: Retrofit
    ) = retrofit.create(VoucherService::class.java)

    @Provides
    @Singleton
    fun provideOrderService(
        retrofit: Retrofit
    ) = retrofit.create(OrderService::class.java)

    @Provides
    @Singleton
    fun provideCommentService(
        retrofit: Retrofit
    ) = retrofit.create(CommentService::class.java)
}