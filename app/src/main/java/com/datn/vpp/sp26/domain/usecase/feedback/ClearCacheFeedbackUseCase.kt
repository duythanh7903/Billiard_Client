package com.datn.vpp.sp26.domain.usecase.feedback

import com.datn.vpp.sp26.domain.repository.FeedbackRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearCacheFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    operator fun invoke() = flow {
        emit(feedbackRepository.clearCacheFeedBack())
    }
}