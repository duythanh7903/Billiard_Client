package com.datn.vpp.sp26.domain.usecase.comment

import android.content.Context
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.domain.model.dto.req.ReqCommentDTO
import com.datn.vpp.sp26.domain.repository.CommentRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val commentRepository: CommentRepository
) {
    operator fun invoke(req: ReqCommentDTO) = flow {
        emit(UiState.Loading)

        try {
            when (val response = commentRepository.createComment(req)) {
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