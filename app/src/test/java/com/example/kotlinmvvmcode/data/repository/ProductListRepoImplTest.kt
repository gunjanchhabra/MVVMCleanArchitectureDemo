package com.example.kotlinmvvmcode.data.repository

import com.example.kotlinmvvmcode.Dispatcher
import com.example.kotlinmvvmcode.TestData.mappedResponseProductList
import com.example.kotlinmvvmcode.TestData.networkResponseProductList
import com.example.kotlinmvvmcode.TestData.productItemModel
import com.example.kotlinmvvmcode.TestData.productsItemDataModel
import com.example.kotlinmvvmcode.data.network.ApiService
import com.example.kotlinmvvmcode.data.mapper.ProductsItemMapper
import com.example.kotlinmvvmcode.domain.repository.ProductRepo
import com.example.kotlinmvvmcode.utils.ApiResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductListRepoImplTest {

    @get:Rule
    val coroutineScope = Dispatcher()

    @MockK
    lateinit var apiService: ApiService

    @MockK
    lateinit var productItemMapper: ProductsItemMapper

    lateinit var productListRepo: ProductRepo

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, true)
        productListRepo = ProductRepoImpl(apiService, productItemMapper, testDispatcher)
    }


    @Test
    fun fetchProductsList_success() = runBlocking {
        val networkProductList = networkResponseProductList()
        val mappedProductList = mappedResponseProductList()
        val productResponse = Response.success(networkProductList)
        coEvery { apiService.getProducts() } returns Response.success(productResponse.body())
        coEvery { productItemMapper.mapToDomain(any()) } returns mappedProductList.first()
        val result = productListRepo.fetchProductsList().last()
        assertEquals(result, ApiResponse.Success(mappedProductList))
        coVerifySequence {
            apiService.getProducts()
            productItemMapper.mapToDomain(any())
        }
    }

    @Test
    fun fetchProductsList_error() = runBlocking {
        coEvery { apiService.getProducts() } returns Response.error(404, mockk(relaxed = true))
        val result = productListRepo.fetchProductsList().last()
        assertEquals((result as ApiResponse.Error).message, "Response.error()")
    }

    @Test
    fun fetchProductDetail_success() = runBlocking {
        val networkProduct = productsItemDataModel
        val mappedProduct = productItemModel
        val productResponse = Response.success(networkProduct)
        coEvery { apiService.getProductDetail(1) } returns Response.success(productResponse.body())
        coEvery { productItemMapper.mapToDomain(any()) } returns mappedProduct
        val result = productListRepo.fetchProductDetail(1).last()
        assertEquals(ApiResponse.Success(mappedProduct), result)
    }

    @Test
    fun fetchProductDetail_error() = runBlocking {
        coEvery { apiService.getProductDetail(1) } returns Response.error(
            404,
            mockk(relaxed = true)
        )
        val result = productListRepo.fetchProductDetail(1).last()
        assertEquals("Response.error()", (result as ApiResponse.Error).message)
    }
}
