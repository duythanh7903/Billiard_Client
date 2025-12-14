package com.datn.bia.a.domain.usecase.order

import android.content.Context
import android.util.Log
import com.datn.bia.a.R
import com.datn.bia.a.common.UiState
import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.repository.OrderRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class GetAllOrderUseCase @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val orderRepository: OrderRepository
) {
    operator fun invoke() = flow {
        emit(UiState.Loading)

        try {
            when (val response = orderRepository.getAllOrder()) {
                is ResultWrapper.Success -> emit(UiState.Success(response.value))

                is ResultWrapper.GenericError -> {
                    Log.d("debug", response.message ?: "")

                    emit(UiState.Error(response.message?.ifEmpty {
                        context.getString(R.string.msg_wrong)
                    } ?: "Unknow Error"))
                }

                is ResultWrapper.NetworkError -> emit(UiState.Error("Network Error"))
            }
        } catch (e: HttpException) {
            emit(UiState.Error(e.message ?: "Unknow Error"))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Unknow Error"))
        }

    }.flowOn(Dispatchers.IO)
}