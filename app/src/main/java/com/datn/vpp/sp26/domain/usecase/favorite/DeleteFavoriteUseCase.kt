package com.datn.vpp.sp26.domain.usecase.favorite

import com.datn.vpp.sp26.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(id: String) = favoriteRepository.deleteFavoriteByProductId(id)
}