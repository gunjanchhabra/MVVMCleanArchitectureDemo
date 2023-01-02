package com.example.kotlinmvvmcode.view.viewmodel

import com.example.kotlinmvvmcode.Dispatcher
import com.example.kotlinmvvmcode.TestData
import com.example.kotlinmvvmcode.domain.usecase.ProductDetailUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.view.model.mapper.ProductListUiMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductDetailViewModelTest {

    lateinit var productDetailViewModel: ProductDetailViewModel

    @RelaxedMockK
    lateinit var productListUseCase: ProductDetailUseCase

    @RelaxedMockK
    lateinit var productListUiMapper: ProductListUiMapper

    @get:Rule
    var dispatcher = Dispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        productDetailViewModel = ProductDetailViewModel(productListUseCase, productListUiMapper)
    }

    @Test
    fun getProductsDetail_success() = runBlocking {
        val productDomainResponse = ApiResponse.Success(TestData.productItemModel)
        val productMappedUiResponse = TestData.productItemUiModel
        coEvery { productListUseCase(1) } returns flowOf(productDomainResponse)
        coEvery { productListUiMapper.mapToUi(any()) } returns productMappedUiResponse
        productDetailViewModel.getDetailOfProduct(1)
        assertEquals(
            productMappedUiResponse,
            productDetailViewModel.productDetailStateFlow.value.data
        )
    }

    @Test
    fun getProductsList_fail() = runBlocking {
        val errorMsg = "Internal Server Error"
        coEvery { productListUseCase(1) } returns flowOf(ApiResponse.Error(errorMsg))
        productDetailViewModel.getDetailOfProduct(1)
        assertEquals(errorMsg, productDetailViewModel.productDetailStateFlow.value.message)
    }
}
