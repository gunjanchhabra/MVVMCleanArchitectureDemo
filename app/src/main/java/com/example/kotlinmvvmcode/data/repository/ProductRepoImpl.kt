package com.example.kotlinmvvmcode.data.repository

import com.example.kotlinmvvmcode.data.mapper.ProductsItemMapper
import com.example.kotlinmvvmcode.data.network.ApiService
import com.example.kotlinmvvmcode.di.DispatcherModule
import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.domain.repository.ProductRepo
import com.example.kotlinmvvmcode.utils.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val productsItemMapper: ProductsItemMapper,
    @DispatcherModule.IoDispatcher private val ioCoroutineDispatcher: CoroutineDispatcher
) : ProductRepo {

    override fun fetchProductsList(): Flow<ApiResponse<List<ProductItemDomainModel>>> {
        return flow {
            emit(ApiResponse.Loading)
            try {
                val response = apiService.getProducts()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ApiResponse.Success(
                            it.map { productsItemMapper.mapToDomain(it) }
                        ))
                    }
                } else {
                    emit(ApiResponse.Error(response.message() ?: ""))
                }
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.localizedMessage ?: ""))
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.localizedMessage ?: ""))
            }
        }.flowOn(ioCoroutineDispatcher)
    }

    override fun fetchProductDetail(id: Int): Flow<ApiResponse<ProductItemDomainModel>> {
        return flow {
            emit(ApiResponse.Loading)
            try {
                val response = apiService.getProductDetail(id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ApiResponse.Success(productsItemMapper.mapToDomain(it)))
                    }
                } else {
                    emit(ApiResponse.Error(response.message() ?: ""))
                }
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.localizedMessage ?: ""))
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.localizedMessage ?: ""))
            }
        }.flowOn(ioCoroutineDispatcher)
    }
}
