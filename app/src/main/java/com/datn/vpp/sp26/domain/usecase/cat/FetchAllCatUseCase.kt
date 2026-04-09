package com.datn.vpp.sp26.domain.usecase.cat

import android.content.Context
import com.datn.vpp.sp26.R
import com.datn.vpp.sp26.common.UiState
import com.datn.vpp.sp26.common.toListCategoryEntities
import com.datn.vpp.sp26.data.network.factory.ResultWrapper
import com.datn.vpp.sp26.domain.repository.CatRepository
import com.datn.vpp.sp26.domain.usecase.cat_cache.CacheCatUseCase
import com.datn.vpp.sp26.domain.usecase.cat_cache.ClearCacheCatUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class FetchAllCatUseCase @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val catRepository: CatRepository,

    private val cacheCatUseCase: CacheCatUseCase,
    private val clearCacheCatUseCase: ClearCacheCatUseCase
) {
    operator fun invoke() = flow {
        emit(UiState.Loading)

        try {
            when (val response = catRepository.fetchAllCat()) {
                is ResultWrapper.Success -> {
                    clearCacheCatUseCase.invoke().collect {
                        cacheCatUseCase.invoke(response.value.toListCategoryEntities()).collect { }
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
    }
}