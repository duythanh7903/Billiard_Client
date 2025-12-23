package com.datn.bia.a.domain.usecase.cart

import com.datn.bia.a.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IncreaseCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(idCart: Long) = flow {
        // check cart da ton tai truoc do hay chua
        val cartEntity = cartRepository.searchCartEnableByIdCart(idCart)

        if (cartEntity == null) { // truong hop nay la truong hop cart chua ton tai
            emit(false)
            return@flow
        }

        val newCartEntity = cartEntity.copy(
            quantity = cartEntity.quantity + 1 // tang so luong len 1
        )

        cartRepository.updateCart(newCartEntity)
        emit(true)
    }
}