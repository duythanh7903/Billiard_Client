package com.datn.vpp.sp26.domain.usecase.order

import android.content.Context
import android.util.Log
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.domain.model.dto.req.ReqUpdateOrder
import com.datn.vpp.sp26.domain.repository.OrderRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val orderRepository: OrderRepository
) {
    operator fun invoke(
        idOrder: String,
        reqUpdateOrder: ReqUpdateOrder
    ) = flow {
        emit(UiState.Loading)

        try {
            when (val response = orderRepository.updateOrder(
                idOrder, reqUpdateOrder
            )) {
                is ResultWrapper.Success -> emit(UiState.Success(response.value))

                is ResultWrapper.GenericError -> {
                    Log.d("debug", "ResultWrapper.GenericError: " + response.message ?: "")

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
    }
}