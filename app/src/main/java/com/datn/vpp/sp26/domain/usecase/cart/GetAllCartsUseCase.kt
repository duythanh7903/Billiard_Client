package com.datn.vpp.sp26.domain.usecase.cart

import com.datn.vpp.sp26.domain.repository.CartRepository
import javax.inject.Inject

class GetAllCartsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke() = cartRepository.getAllCartEnable()
}