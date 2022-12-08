package com.example.kotlinmvvmcode.domain.usecase

import com.example.kotlinmvvmcode.datamodel.model.ProductItemModel
import com.example.kotlinmvvmcode.domain.repository.ProductRepo
import com.example.kotlinmvvmcode.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductUseCaseImpl @Inject constructor(val productListRepo: ProductRepo) : ProductUseCase {

    override suspend fun fetchProducts(): Flow<ApiResponse<List<ProductItemModel>?>>  {
        return productListRepo.fetchProductsList()
    }

    override suspend fun fetchProductDetail(id: Int) : Flow<ApiResponse<ProductItemModel>> {
        return productListRepo.fetchProductDetail(id)
    }
}