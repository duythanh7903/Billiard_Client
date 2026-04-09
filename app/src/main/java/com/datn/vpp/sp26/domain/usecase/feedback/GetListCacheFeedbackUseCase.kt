package com.datn.vpp.sp26.domain.usecase.feedback

import com.datn.vpp.sp26.domain.repository.FeedbackRepository
import javax.inject.Inject

class GetListCacheFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    operator fun invoke() = feedbackRepository.getListFeedBack()
}