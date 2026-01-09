package com.datn.bia.a.domain.usecase.feedback

import com.datn.bia.a.domain.model.entity.FeedbackEntity
import com.datn.bia.a.domain.repository.FeedbackRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CacheFeedbackUseCase @Inject constructor(
    private val feedbackRepository: FeedbackRepository
) {
    operator fun invoke(list: List<FeedbackEntity>) = flow {
        emit(feedbackRepository.cacheListFeedBack(list))
    }
}