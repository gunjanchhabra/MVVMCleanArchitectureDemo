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

class ProductListUseCaseImplTest{

    lateinit var productListUseCase: ProductListUseCase

    @RelaxedMockK
    lateinit var productListRepo: ProductRepo


    @Before
    fun setup() {
        MockKAnnotations.init(this,true)
        productListUseCase = ProductListUseCase(productListRepo)
    }

    @Test
    fun fetchProducts_success() = runBlocking {
        val productDomainResponse = ApiResponse.success(TestData.mappedResponseProductList())
        coEvery { productListRepo.fetchProductsList() } returns flowOf(productDomainResponse)
        productListUseCase().collect{
            assertNotNull(it)
            assertEquals(productDomainResponse, it)
        }
    }

    @Test
    fun fetchProducts_error() = runBlocking{
        val errorString = "Internal Server Error"
        val productResponse = ApiResponse.error(null, errorString)
        coEvery { productListRepo.fetchProductsList() } returns flowOf(productResponse)
        productListUseCase().collect{
            assertNull(it.data)
            assertEquals(errorString, it.message)
        }
    }

}