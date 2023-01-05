package com.example.kotlinmvvmcode.domain.usecase

import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.domain.repository.ProductRepo
import com.example.kotlinmvvmcode.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductListUseCase @Inject constructor(private val productListRepo: ProductRepo) {

    operator fun invoke(): Flow<ApiResponse<List<ProductItemDomainModel>>> =
        productListRepo.fetchProductsList()
}
