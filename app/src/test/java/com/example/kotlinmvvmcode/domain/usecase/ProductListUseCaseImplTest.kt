package com.example.kotlinmvvmcode.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlinmvvmcode.TestData.productItemModel
import com.example.kotlinmvvmcode.datamodel.model.ProductItemModel
import com.example.kotlinmvvmcode.domain.repository.ProductRepo
import com.example.kotlinmvvmcode.utils.ApiResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductListUseCaseImplTest{

    lateinit var productListUseCase: ProductUseCase

    @RelaxedMockK
    lateinit var productListRepo: ProductRepo


    @Before
    fun setup() {
        MockKAnnotations.init(this,true)
        productListUseCase = ProductUseCaseImpl(productListRepo)
    }

    @Test
    fun fetchProducts_success() {
        val productItemList : List<ProductItemModel> = listOf()
        val productResponse = ApiResponse.success(productItemList)
        coEvery { productListRepo.fetchProductsList() } returns flowOf(productResponse)
        runBlocking {
           val response =  productListUseCase.fetchProducts().single()
            assertEquals(
                response.data,productItemList
            )
        }
    }

    @Test
    fun fetchProducts_error() {
        val errorString = "Internal Server Error"
        val productResponse = ApiResponse.error(null, errorString)
        coEvery { productListRepo.fetchProductsList() } returns flowOf(productResponse)
        runBlocking {
            val response =  productListUseCase.fetchProducts().single()
            assertEquals(
                response.message,errorString
            )
        }
    }

    @Test
    fun fetchProductDetail_success() {
        val productItem : ProductItemModel = productItemModel
        val productResponse = ApiResponse.success(productItem)
        coEvery { productListRepo.fetchProductDetail(1) } returns flowOf(productResponse)
        runBlocking {
            val response =  productListUseCase.fetchProductDetail(1).single()
            assertEquals(
                response.data,productItem
            )
        }
    }

    @Test
    fun fetchProductDetail_error() {
        val errorString = "Internal Server Error"
        val productResponse = ApiResponse.error(null, errorString)
        coEvery { productListRepo.fetchProductDetail(1) } returns flowOf(productResponse)
        runBlocking {
            val response =  productListUseCase.fetchProductDetail(1).single()
            assertEquals(
                response.message,errorString
            )
        }
    }

}