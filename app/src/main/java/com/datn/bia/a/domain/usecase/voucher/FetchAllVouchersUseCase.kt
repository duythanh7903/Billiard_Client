package com.datn.bia.a.domain.usecase.voucher

import android.content.Context
import android.util.Log
import com.datn.bia.a.R
import com.datn.bia.a.common.UiState
import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.repository.VoucherRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class FetchAllVouchersUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val voucherRepository: VoucherRepository
) {
    operator fun invoke() = flow {
        emit(UiState.Loading)

        try {
            when (val response = voucherRepository.fetchAllVouchers()) {
                is ResultWrapper.Success -> emit(UiState.Success(response.value))

                is ResultWrapper.GenericError -> {
                    Log.d("debug", "ResultWrapper.GenericError: ${response.message}")

                    emit(UiState.Error(response.message?.ifEmpty {
                        context.getString(R.string.msg_wrong)
                    } ?: "Unknow Error"))
                }

                is ResultWrapper.NetworkError -> emit(UiState.Error("Network Error"))
            }
        } catch (e: HttpException) {
            Log.d("debug", "HttpException: ${e.message}")
            emit(UiState.Error(e.message ?: "Unknow Error"))
        } catch (e: Exception) {
            Log.d("debug", "Exception: ${e.message}")
            emit(UiState.Error(e.message ?: "Unknow Error"))
        }
    }
}