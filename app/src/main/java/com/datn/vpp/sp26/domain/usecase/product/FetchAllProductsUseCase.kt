package com.datn.vpp.sp26.domain.usecase.product

import android.content.Context
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.toListCacheProduct
import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.domain.repository.ProductRepository
import com.datn.vpp.sp26.domain.usecase.prod_cache.CacheProdUseCase
import com.datn.vpp.sp26.domain.usecase.prod_cache.ClearCacheProdUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class FetchAllProductsUseCase @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val productRepository: ProductRepository,

    private val clearCacheProdUseCase: ClearCacheProdUseCase,
    private val cacheProdUseCase: CacheProdUseCase
) {
    operator fun invoke() = flow {
        emit(UiState.Loading) // show loading

        try {
            // nhan ket qua tu api trar ve
            when (val response = productRepository.fetchAllProducts()) {
                is ResultWrapper.Success -> {
                    val data = response.value.data
                    data?.let { listResponse ->
                        clearCacheProdUseCase.invoke().collect {
                            val listCache = listResponse.toListCacheProduct()
                            cacheProdUseCase.invoke(listCache).collect { }
                        }
                    }

                    emit(UiState.Success(response.value))
                }

                is ResultWrapper.GenericError -> emit(UiState.Error(response.message?.ifEmpty {
                    context.getString(R.string.msg_wrong)
                } ?: "Unknow Error"))

                is ResultWrapper.NetworkError -> emit(UiState.Error("Network Error"))
            }
        } catch (e: HttpException) {
            emit(UiState.Error(e.message ?: "Unknow Error"))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "Unknow Error"))
        }
    }.flowOn(Dispatchers.IO)
}