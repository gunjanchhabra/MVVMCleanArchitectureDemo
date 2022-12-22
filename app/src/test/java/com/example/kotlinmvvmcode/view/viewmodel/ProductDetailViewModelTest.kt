package com.example.kotlinmvvmcode.view.viewmodel

import com.example.kotlinmvvmcode.Dispatcher
import com.example.kotlinmvvmcode.TestData
import com.example.kotlinmvvmcode.datamodel.mapper.Mapper
import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.domain.usecase.ProductDetailUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.utils.Status
import com.example.kotlinmvvmcode.view.model.mapper.ProductListUiMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
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
            val productDomainResponse = ApiResponse.success(TestData.productItemModel)
            val productMappedUiResponse = TestData.productItemUiModel
            coEvery { productListUseCase(1) } returns flowOf(productDomainResponse)
            coEvery { productListUiMapper.mapFromModel(any()) } returns productMappedUiResponse
            productDetailViewModel.getDetailOfProduct(1)
            assertEquals(ApiResponse(Status.SUCCESS, productMappedUiResponse, ""), productDetailViewModel.productDetailStateFlow.value)
    }


    @Test
    fun getProductsList_fail() = runBlocking {
        val errorMsg = "Internal Server Error"
        val productResponse = ApiResponse.error(null, errorMsg)
        coEvery { productListUseCase(1) } returns flowOf(productResponse)
        productDetailViewModel.getDetailOfProduct(1)
        assertEquals(ApiResponse(Status.ERROR, null, errorMsg), productDetailViewModel.productDetailStateFlow.value)
    }

}