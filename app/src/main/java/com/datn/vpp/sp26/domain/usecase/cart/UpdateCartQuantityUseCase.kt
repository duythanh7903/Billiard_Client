package com.datn.vpp.sp26.domain.usecase.cart

import com.datn.vpp.sp26.domain.repository.CartRepository
import javax.inject.Inject

class UpdateCartQuantityUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(quantity: Int, id: Long) =
        cartRepository.updateQuantity(quantity, id)
}