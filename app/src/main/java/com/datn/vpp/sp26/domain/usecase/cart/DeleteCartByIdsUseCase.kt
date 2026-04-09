package com.datn.vpp.sp26.domain.usecase.cart

import com.datn.vpp.sp26.domain.repository.CartRepository
import javax.inject.Inject

class DeleteCartByIdsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(ids: List<Long>) =
        cartRepository.deleteByIds(ids)
}