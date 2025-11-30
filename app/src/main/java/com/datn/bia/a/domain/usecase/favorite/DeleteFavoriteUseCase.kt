package com.datn.bia.a.domain.usecase.favorite

import com.datn.bia.a.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(id: String) = favoriteRepository.deleteFavoriteByProductId(id)
}