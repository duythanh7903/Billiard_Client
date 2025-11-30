package com.datn.bia.a.domain.usecase.favorite

import com.datn.bia.a.domain.model.entity.FavoriteEntity
import com.datn.bia.a.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(e: FavoriteEntity) = favoriteRepository.insertFavorite(e)
}