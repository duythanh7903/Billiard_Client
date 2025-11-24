package com.datn.bia.a.data.network.repository

import com.datn.bia.a.data.network.factory.ResultWrapper
import com.datn.bia.a.data.network.service.ProductService
import com.datn.bia.a.domain.model.dto.res.ResProductDTO
import com.datn.bia.a.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService
): ProductRepository {
    override suspend fun fetchAllProducts(): ResultWrapper<ResProductDTO> =
        productService.fetchAllProducts()
}