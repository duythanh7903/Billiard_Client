package com.datn.bia.a.domain.usecase.favorite

import com.datn.bia.a.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke() = favoriteRepository.getAllFavorite()
}