package com.datn.bia.a.domain.usecase.cart

import com.datn.bia.a.domain.model.entity.CartEntity
import com.datn.bia.a.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
){
    operator fun invoke(id: String) = flow {
        cartRepository.searchCartByIdProd(id)?.let { cart ->
            val newCart = cart.copy(
                quantity = cart.quantity + 1
            )

            cartRepository.updateCart(newCart)
        } ?: run {
            cartRepository.insertCart(
                CartEntity(
                    idProd = id,
                    quantity = 1,
                    isEnable = true
                )
            )
        }

        emit(true)
    }
}