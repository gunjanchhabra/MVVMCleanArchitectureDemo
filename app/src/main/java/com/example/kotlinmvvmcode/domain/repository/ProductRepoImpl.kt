package com.example.kotlinmvvmcode.domain.repository

import com.example.kotlinmvvmcode.domain.model.mapper.ProductsItemMapper
import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.domain.ApiService
import com.example.kotlinmvvmcode.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(val apiService: ApiService, val productsItemMapper: ProductsItemMapper) : ProductRepo {

    override fun fetchProductsList(): Flow<ApiResponse<List<ProductItemDomainModel>?>> = flow {
        emit(ApiResponse.loading(null))
        try {
            val response = apiService.getProducts()
            if (response.isSuccessful){
                response.body()?.let {
                    emit(ApiResponse.success(
                       it.map { productsItemMapper.mapFromModel(it) }
                    ))
                }
            }else{
                emit(ApiResponse.error(null, response.message() ?: ""))
            }
        }catch (e : HttpException){
            emit(ApiResponse.error(null, e.localizedMessage ?: ""))
        }catch (e : IOException){
            emit(ApiResponse.error(null, e.localizedMessage ?: ""))
        }
    }

    override fun fetchProductDetail(id: Int) : Flow<ApiResponse<ProductItemDomainModel>> = flow {
        emit(ApiResponse.loading(null))
        try {
            val response = apiService.getProductDetail(id)
            if (response.isSuccessful){
                response.body()?.let {
                    emit(ApiResponse.success(productsItemMapper.mapFromModel(it)))
                }
            }else{
                emit(ApiResponse.error(null, response.message() ?: ""))
            }
        }catch (e : HttpException){
            emit(ApiResponse.error(null, e.localizedMessage ?: ""))
        }catch (e : IOException){
            emit(ApiResponse.error(null, e.localizedMessage ?: ""))
        }
    }
}