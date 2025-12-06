package com.datn.bia.a.domain.usecase.auth

import android.content.Context
import com.datn.bia.a.R
import com.datn.bia.a.common.UiState
import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.domain.model.dto.req.ReqUpdateAddressDTO
import com.datn.bia.a.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class UpdateAddressUseCase @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        id: String,
        req: ReqUpdateAddressDTO
    ) = flow {
        emit(UiState.Loading)

        try {
            when (val response = authRepository.updateAddress(id, req)) {
                is ResultWrapper.Success -> emit(UiState.Success(response.value))

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
    }
}