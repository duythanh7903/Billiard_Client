package com.datn.vpp.sp26.domain.repository

import com.datn.vpp.sp26.domain.model.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun insertCart(c: CartEntity)
    suspend fun searchCartByIdProd(id: String): List<CartEntity>
    suspend fun updateCart(c: CartEntity)
    fun getAllCartEnable(idUser: String): Flow<List<CartEntity>>
    suspend fun searchCartEnableByIdCart(id: Long): CartEntity?
    suspend fun deleteCart(c: CartEntity)
    suspend fun deleteByIds(ids: List<Long>)
    suspend fun searchCartByIdAndUser(id: String, idUser: String): List<CartEntity>
}