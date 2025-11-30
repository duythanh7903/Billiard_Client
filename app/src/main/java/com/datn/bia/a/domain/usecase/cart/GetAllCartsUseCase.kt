package com.datn.bia.a.domain.usecase.cart

import com.datn.bia.a.domain.repository.CartRepository
import javax.inject.Inject

class GetAllCartsUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke() = cartRepository.getAllCartEnable()
}