package com.datn.bia.a.domain.usecase.order

import android.content.Context
import com.datn.bia.a.R
import com.datn.bia.a.common.UiState
import com.datn.bia.a.common.toListOrderEntity
import com.datn.bia.a.data.network.factory.ResultWrapper
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

    private val clearAllOrderCacheUseCase: ClearAllOrderCacheUseCase,
    private val cacheListOrderUseCase: CacheListOrderUseCase
) {
    operator fun invoke(id: String) = flow {
        emit(UiState.Loading)

        while (true) {
            try {
                when (val response = orderRepository.getOrdersByUser(id)) {
                    is ResultWrapper.Success -> {
                        val data = response.value.toListOrderEntity()
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
}