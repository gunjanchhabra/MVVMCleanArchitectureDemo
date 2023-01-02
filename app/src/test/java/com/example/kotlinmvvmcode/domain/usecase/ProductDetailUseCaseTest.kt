package com.example.kotlinmvvmcode.domain.usecase

import com.example.kotlinmvvmcode.TestData
import com.example.kotlinmvvmcode.data.repository.ProductRepo
import com.example.kotlinmvvmcode.utils.ApiResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductDetailUseCaseTest {
    lateinit var productDetailUseCase: ProductDetailUseCase

    @RelaxedMockK
    lateinit var productListRepo: ProductRepo


    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        productDetailUseCase = ProductDetailUseCase(productListRepo)
    }

    @Test
    fun fetchProductDetail_success() = runBlocking {
        val productDomainResponse = ApiResponse.Success(TestData.productItemModel)
        coEvery { productListRepo.fetchProductDetail(1) } returns flowOf(productDomainResponse)
        productDetailUseCase(1).collect {
            assertNotNull(it)
            assertEquals(productDomainResponse, it)
        }
    }

    @Test
    fun fetchProductDetail_error() = runBlocking {
        val errorString = "Internal Server Error"
        coEvery { productListRepo.fetchProductDetail(1) } returns flowOf(
            ApiResponse.Error(
                errorString
            )
        )
        productDetailUseCase(1).collect {
            assertNull(it.data)
            assertEquals(errorString, it.message)
        }
    }
}
