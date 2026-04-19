package com.datn.vpp.sp26.domain.usecase.cart

import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.domain.model.dto.res.ResVariantDTO
import com.datn.vpp.sp26.domain.model.entity.CartEntity
import com.datn.vpp.sp26.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(
        id: String,
        variant: ResVariantDTO,
        price: Double,
    ) = flow {
        val listSearch = cartRepository.searchCartByIdAndUser(id, SharedPrefCommon.idUser)

        val response =
            listSearch.firstOrNull { it.idProd == id && it.variant.color == variant.color }
        if (response != null) { // update
            val newCart = response.copy(
                quantity = response.quantity + if (SharedPrefCommon.role == AppConst.ROLE_WHOLESALE) 100 else 1
            )

            cartRepository.updateCart(newCart)
        } else { // insert
            cartRepository.insertCart(
                CartEntity(
                    idProd = id,
                    quantity = 100,
                    isEnable = true,
                    variant = variant,
                    price = price
                )
            )
        }

        emit(true)
    }
}