package com.datn.bia.a.domain.repository

import com.datn.bia.a.domain.model.entity.CartEntity

interface CartRepository {
    suspend fun insertCart(c: CartEntity)
    suspend fun searchCartByIdProd(id: String): CartEntity?
    suspend fun updateCart(c: CartEntity)
}