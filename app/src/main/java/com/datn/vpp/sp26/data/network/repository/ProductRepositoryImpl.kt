package com.datn.vpp.sp26.data.network.repository

import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.data.network.service.ProductService
import com.datn.vpp.sp26.domain.model.dto.res.ResProductDTO
import com.datn.vpp.sp26.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService
): ProductRepository {
    override suspend fun fetchAllProducts(): ResultWrapper<ResProductDTO> =
        productService.fetchAllProducts()
}