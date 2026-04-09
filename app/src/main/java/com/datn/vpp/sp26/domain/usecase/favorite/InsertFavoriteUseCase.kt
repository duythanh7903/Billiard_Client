package com.datn.vpp.sp26.domain.usecase.favorite

import com.datn.vpp.sp26.domain.model.entity.FavoriteEntity
import com.datn.vpp.sp26.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(e: FavoriteEntity) = favoriteRepository.insertFavorite(e)
}