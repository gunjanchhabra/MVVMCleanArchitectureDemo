package com.example.kotlinmvvmcode.view.viewmodel

import com.example.kotlinmvvmcode.Dispatcher
import com.example.kotlinmvvmcode.TestData
import com.example.kotlinmvvmcode.datamodel.model.ProductItemModel
import com.example.kotlinmvvmcode.domain.usecase.ProductUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.utils.Status
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductDetailViewModelTest{

    lateinit var productDetailViewModel: ProductDetailViewModel

    @RelaxedMockK
    lateinit var productListUseCase: ProductUseCase

    @get:Rule
    var dispatcher = Dispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this,true)
        productDetailViewModel = ProductDetailViewModel(productListUseCase)
    }

    @Test
    fun getProductsDetail_success() {

        runBlocking{
            val productItem : ProductItemModel = TestData.productItemModel
            val productResponse = ApiResponse.success(productItem)
            coEvery { productListUseCase.fetchProductDetail(1) } returns flowOf(productResponse)
                productDetailViewModel.getDetailOfProduct(1)
                assertEquals(
                    ApiResponse(Status.SUCCESS,productItem, ""),productDetailViewModel._productDetailStateFlow.value
                )
        }
    }

    @Test
    fun getProductsList_fail() {
        val errorMsg = "Internal Server Error"
        val productResponse = ApiResponse.error(null, errorMsg)
        coEvery { productListUseCase.fetchProductDetail(1) } returns flowOf(productResponse)
        runBlocking {
            productDetailViewModel.getDetailOfProduct(1)
            assertEquals(
                ApiResponse(Status.ERROR,null, errorMsg),productDetailViewModel._productDetailStateFlow.value
            )
        }
    }
}