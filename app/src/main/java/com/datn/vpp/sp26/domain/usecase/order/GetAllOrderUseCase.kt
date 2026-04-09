package com.datn.vpp.sp26.domain.usecase.order

import android.content.Context
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.domain.repository.OrderRepository
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
                is ResultWrapper.Success -> {
                    val data = response.value

                    emit(UiState.Success(response.value))
                }

                is ResultWrapper.GenericError ->
                    emit(UiState.Error(response.message?.ifEmpty {
                        context.getString(R.string.msg_wrong)
                    } ?: "Unknow Error"))

                is ResultWrapper.NetworkError -> emit(UiState.Error("Network Error"))
            }
        } catch (e: HttpException) {
            emit(UiState.Error(e.message ?: "Unknow Error"))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Unknow Error"))
        }

    }.flowOn(Dispatchers.IO)
}