package com.example.kotlinmvvmcode.domain.usecase

import com.example.kotlinmvvmcode.datamodel.data.ProductsDataModel
import com.example.kotlinmvvmcode.datamodel.model.ProductItemModel
import com.example.kotlinmvvmcode.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProductUseCase {

    suspend fun fetchProducts() : Flow<ApiResponse<List<ProductItemModel>?>>

    suspend fun fetchProductDetail(id: Int) : Flow<ApiResponse<ProductItemModel>>
}