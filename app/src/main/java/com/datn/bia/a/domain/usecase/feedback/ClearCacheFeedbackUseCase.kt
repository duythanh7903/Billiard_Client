package com.datn.bia.a.domain.usecase.feedback

import com.datn.bia.a.domain.repository.FeedbackRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ClearCacheFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    operator fun invoke() = flow {
        emit(feedbackRepository.clearCacheFeedBack())
    }
}