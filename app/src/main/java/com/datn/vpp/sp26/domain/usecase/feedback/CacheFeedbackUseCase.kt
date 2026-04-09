package com.datn.vpp.sp26.domain.usecase.feedback

import com.datn.vpp.sp26.domain.model.entity.FeedbackEntity
import com.datn.vpp.sp26.domain.repository.FeedbackRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CacheFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    operator fun invoke(list: List<FeedbackEntity>) = flow {
        emit(feedbackRepository.cacheListFeedBack(list))
    }
}