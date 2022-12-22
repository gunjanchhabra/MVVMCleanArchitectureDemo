package com.example.kotlinmvvmcode.domain.repository

import com.example.kotlinmvvmcode.TestData.mappedResponseProductList
import com.example.kotlinmvvmcode.TestData.networkResponseProductList
import com.example.kotlinmvvmcode.TestData.productItemModel
import com.example.kotlinmvvmcode.TestData.productsItemDataModel
import com.example.kotlinmvvmcode.domain.model.mapper.ProductsItemMapper
import com.example.kotlinmvvmcode.domain.ApiService
import com.example.kotlinmvvmcode.utils.Status
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductListRepoImplTest {

    @MockK
    lateinit var apiService : ApiService

    @MockK
    lateinit var productItemMapper: ProductsItemMapper

    lateinit var productListRepo: ProductRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this,true)
        productListRepo = ProductRepoImpl(apiService, productItemMapper)
    }


    @Test
    fun fetchProductsList_success() = runBlocking {
        val networkProductList = networkResponseProductList()
        val mappedProductList = mappedResponseProductList()
        val productResponse = Response.success(networkProductList)
        coEvery { apiService.getProducts() } returns Response.success(productResponse.body())
        coEvery { productItemMapper.mapFromModel(any()) } returns mappedProductList.first()
        val result = productListRepo.fetchProductsList().last()
        assertEquals(result.data, mappedProductList)
        coVerifySequence {
            apiService.getProducts()
            productItemMapper.mapFromModel(any())
        }
    }

    @Test
    fun fetchProductsList_error() = runBlocking {
        coEvery { apiService.getProducts() } returns Response.error(404, mockk(relaxed = true))
        val result = productListRepo.fetchProductsList().last()
        assertEquals(Status.ERROR, result.status)
    }

    @Test
    fun fetchProductDetail_success() = runBlocking {
        val networkProduct = productsItemDataModel
        val mappedProduct = productItemModel
        val productResponse = Response.success(networkProduct)
        coEvery { apiService.getProductDetail(1) } returns Response.success(productResponse.body())
        coEvery { productItemMapper.mapFromModel(any()) } returns mappedProduct
        val result = productListRepo.fetchProductDetail(1).last()
        assertEquals(mappedProduct, result.data)
    }

    @Test
    fun fetchProductDetail_error() = runBlocking {
        coEvery { apiService.getProductDetail(1) } returns Response.error(404, mockk(relaxed = true))
        val result = productListRepo.fetchProductDetail(1).last()
        assertEquals(Status.ERROR, result.status)
    }

}