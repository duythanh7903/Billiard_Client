package com.datn.bia.a.data.database.repository

import com.datn.bia.a.data.database.dao.CartDao
import com.datn.bia.a.domain.model.entity.CartEntity
import com.datn.bia.a.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
): CartRepository {
    override suspend fun insertCart(c: CartEntity) =
        cartDao.insertCart(c)

    override suspend fun searchCartByIdProd(id: String): CartEntity? =
        cartDao.searchCartByIdProd(id)

    override suspend fun updateCart(c: CartEntity) =
        cartDao.updateCart(c)
}