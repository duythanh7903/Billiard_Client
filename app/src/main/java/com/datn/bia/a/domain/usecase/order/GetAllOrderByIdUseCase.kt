package com.datn.bia.a.domain.usecase.order

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.asLiveData
import com.datn.bia.a.R
import com.datn.bia.a.common.AppConst
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.toListOrderEntity
import com.datn.bia.a.common.toListResOrderDTO
import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.repository.OrderCacheRepository
import com.datn.bia.a.domain.repository.OrderRepository
import com.datn.bia.a.domain.usecase.order_cache.CacheListOrderUseCase
import com.datn.bia.a.domain.usecase.order_cache.ClearAllOrderCacheUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class GetAllOrderByIdUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val orderRepository: OrderRepository,

    private val orderCacheRepository: OrderCacheRepository,

    private val clearAllOrderCacheUseCase: ClearAllOrderCacheUseCase,
    private val cacheListOrderUseCase: CacheListOrderUseCase,
) {
    operator fun invoke(id: String) = flow {
        emit(UiState.Loading)

        while (true) {
            try {
                when (val response = orderRepository.getOrdersByUser(id)) {
                    is ResultWrapper.Success -> {
                        val list = orderCacheRepository.getAllCacheOrder().asLiveData().value
                            ?: emptyList()
                        val listTemp = list.toListResOrderDTO()
                            .filter { it.status == AppConst.STATUS_ORDER_TO_RECEIVE }
                        val data = response.value.toListOrderEntity()

                        if (listTemp.size < data.filter { it.status == AppConst.STATUS_ORDER_TO_RECEIVE }.size) { // có đơn hàng xác nhận mới  -> push noti
                            createChannel(context)
                            showNotification(context)
                        }

                        clearAllOrderCacheUseCase.invoke().collect {
                            cacheListOrderUseCase.invoke(data).collect {}
                        }

                        emit(UiState.Success(response.value))
                    }

                    is ResultWrapper.GenericError -> emit(UiState.Error(response.message?.ifEmpty {
                        context.getString(R.string.msg_wrong)
                    } ?: "Unknow Error"))

                    is ResultWrapper.NetworkError -> emit(UiState.Error("Network Error"))
                }
            } catch (e: HttpException) {
                emit(UiState.Error(e.message ?: "Unknow Error"))
            } catch (e: Exception) {
                emit(UiState.Error(e.message ?: "Unknow Error"))
            }

            delay(5_000)
        }
    }.flowOn(Dispatchers.IO)

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "high_channel",
                "High Priority",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Heads-up notification"

            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    fun showNotification(context: Context) {
        val notification = NotificationCompat.Builder(context, "high_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.msg_title_noti))
            .setContentText(context.getString(R.string.msg_content_noti))
            // 👇 CỰC KỲ QUAN TRỌNG CHO < ANDROID O
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)
    }
}