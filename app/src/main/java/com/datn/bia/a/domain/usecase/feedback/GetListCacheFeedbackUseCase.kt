package com.datn.bia.a.domain.usecase.feedback

import com.datn.bia.a.domain.repository.FeedbackRepository
import javax.inject.Inject

class GetListCacheFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    operator fun invoke() = feedbackRepository.getListFeedBack()
}