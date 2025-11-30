package com.datn.bia.a.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.datn.bia.a.domain.model.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert
    suspend fun insertCart(c: CartEntity)

    @Query("SELECT * FROM CARTENTITY WHERE ISENABLE == 1 ORDER BY ID DESC")
    fun getAllCartsEnable(): Flow<List<CartEntity>>

    @Query("SELECT * FROM CARTENTITY WHERE IDPROD LIKE :id AND ISENABLE == 1 LIMIT 1")
    suspend fun searchCartByIdProd(id: String): CartEntity?

    @Query("SELECT * FROM CARTENTITY WHERE ID LIKE :id AND ISENABLE == 1 LIMIT 1")
    suspend fun searchCartEnableByIdCart(id: Long): CartEntity?

    @Update
    suspend fun updateCart(c: CartEntity)

    @Delete
    suspend fun deleteCart(c: CartEntity)

}