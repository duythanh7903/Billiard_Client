package com.datn.vpp.sp26.data.database.repository

import com.datn.vpp.sp26.data.database.dao.CartDao
import com.datn.vpp.sp26.domain.model.entity.CartEntity
import com.datn.vpp.sp26.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {
    override suspend fun insertCart(c: CartEntity) =
        cartDao.insertCart(c)

    override suspend fun searchCartByIdProd(id: String): List<CartEntity> =
        cartDao.searchCartByIdProd(id)

    override suspend fun updateCart(c: CartEntity) =
        cartDao.updateCart(c)

    override fun getAllCartEnable(idUser: String): Flow<List<CartEntity>> =
        cartDao.getAllCartsEnable(idUser)

    override suspend fun searchCartEnableByIdCart(id: Long): CartEntity? =
        cartDao.searchCartEnableByIdCart(id)

    override suspend fun deleteCart(c: CartEntity) = cartDao.deleteCart(c)
    override suspend fun deleteByIds(ids: List<Long>) =
        cartDao.deleteByIds(ids)

    override suspend fun searchCartByIdAndUser(
        id: String,
        idUser: String
    ): List<CartEntity> =
        cartDao.searchCartByIdAndUser(id, idUser)

    override suspend fun updateQuantity(quantity: Int, id: Long) =
        cartDao.updateQuantity(quantity, id)
}