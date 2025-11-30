package com.datn.bia.a.domain.usecase.cart

import com.datn.bia.a.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IncreaseCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(idCart: Long) = flow {
        val cartEntity = cartRepository.searchCartEnableByIdCart(idCart)

        if (cartEntity == null) {
            emit(false)
            return@flow
        }

        val newCartEntity = cartEntity.copy(
            quantity = cartEntity.quantity + 1
        )

        cartRepository.updateCart(newCartEntity)
        emit(true)
    }
}