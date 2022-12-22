package com.example.kotlinmvvmcode.domain.usecase

import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.domain.repository.ProductRepo
import com.example.kotlinmvvmcode.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductDetailUseCase @Inject constructor(private val productListRepo: ProductRepo) {

    operator fun invoke(id: Int): Flow<ApiResponse<ProductItemDomainModel>> = productListRepo.fetchProductDetail(id)

}