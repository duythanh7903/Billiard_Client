package com.datn.vpp.sp26.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.datn.vpp.sp26.domain.model.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert
    suspend fun insertCart(c: CartEntity)

    @Query("SELECT * FROM CARTENTITY WHERE ISENABLE == 1 AND idUser == :idUser ORDER BY ID DESC")
    fun getAllCartsEnable(idUser: String): Flow<List<CartEntity>>

    @Query("SELECT * FROM CARTENTITY WHERE IDPROD LIKE :id AND ISENABLE == 1")
    suspend fun searchCartByIdProd(id: String): List<CartEntity>

    @Query("SELECT * FROM CARTENTITY WHERE ID LIKE :id AND ISENABLE == 1 LIMIT 1")
    suspend fun searchCartEnableByIdCart(id: Long): CartEntity?

    @Update
    suspend fun updateCart(c: CartEntity)

    @Delete
    suspend fun deleteCart(c: CartEntity)

    @Query("DELETE FROM CARTENTITY WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Long>)

    @Query("select * from CartEntity where idProd like :id and isEnable == 1 and idUser like :idUser")
    suspend fun searchCartByIdAndUser(id: String, idUser: String): List<CartEntity>

    @Query("update cartentity set quantity = :quantity where id = :id")
    suspend fun updateQuantity(quantity: Int, id: Long)
}