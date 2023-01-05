package com.example.kotlinmvvmcode.domain.repository

import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepo {

    fun fetchProductsList(): Flow<ApiResponse<List<ProductItemDomainModel>>>

    fun fetchProductDetail(id: Int): Flow<ApiResponse<ProductItemDomainModel>>
}
