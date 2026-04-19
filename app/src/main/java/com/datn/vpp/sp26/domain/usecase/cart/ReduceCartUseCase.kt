package com.datn.vpp.sp26.domain.usecase.cart

import com.datn.vpp.sp26.common.AppConst
import com.datn.vpp.sp26.data.storage.SharedPrefCommon
import com.datn.vpp.sp26.domain.repository.CartRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReduceCartUseCase @Inject constructor(
    private val cartRepository: CartRepository
) {
    operator fun invoke(idCart: Long) = flow {
        val cartEntity = cartRepository.searchCartEnableByIdCart(idCart)

        if (cartEntity == null) {
            emit(false)
            return@flow
        }

        val newCartEntity = cartEntity.copy(
            quantity = cartEntity.quantity - if (SharedPrefCommon.role == AppConst.ROLE_WHOLESALE) 10 else 1 // tru so luong di 1
        )

        if (newCartEntity.quantity <= 0) cartRepository.deleteCart(cartEntity)
        else cartRepository.updateCart(newCartEntity) // xoa bo gio hang neu so luong la 0

        emit(true)
    }
}