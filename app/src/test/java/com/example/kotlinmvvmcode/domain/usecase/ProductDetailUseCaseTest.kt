package com.example.kotlinmvvmcode.domain.usecase

import com.example.kotlinmvvmcode.TestData
import com.example.kotlinmvvmcode.domain.repository.ProductRepo
import com.example.kotlinmvvmcode.utils.ApiResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductDetailUseCaseTest{
    lateinit var productDetailUseCase: ProductDetailUseCase

    @RelaxedMockK
    lateinit var productListRepo: ProductRepo


    @Before
    fun setup() {
        MockKAnnotations.init(this,true)
        productDetailUseCase = ProductDetailUseCase(productListRepo)
    }

    @Test
    fun fetchProductDetail_success() = runBlocking {
        val productDomainResponse = ApiResponse.success(TestData.productItemModel)
        coEvery { productListRepo.fetchProductDetail(1) } returns flowOf(productDomainResponse)
        productDetailUseCase(1).collect{
            assertNotNull(it)
            assertEquals(productDomainResponse, it)
        }
    }

    @Test
    fun fetchProductDetail_error() = runBlocking{
        val errorString = "Internal Server Error"
        val productResponse = ApiResponse.error(null, errorString)
        coEvery { productListRepo.fetchProductDetail(1) } returns flowOf(productResponse)
        productDetailUseCase(1).collect{
            assertNull(it.data)
            assertEquals(errorString, it.message)
        }
    }
}