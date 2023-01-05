package com.example.kotlinmvvmcode.view.viewmodel

import com.example.kotlinmvvmcode.Dispatcher
import com.example.kotlinmvvmcode.TestData
import com.example.kotlinmvvmcode.domain.usecase.ProductListUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.view.model.mapper.ProductListUiMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    lateinit var productListViewModel: ProductListViewModel

    @RelaxedMockK
    lateinit var productListUseCase: ProductListUseCase

    @RelaxedMockK
    lateinit var productListUiMapper: ProductListUiMapper

    @get:Rule
    var dispatcher = Dispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        productListViewModel = ProductListViewModel(productListUseCase, productListUiMapper)

    }

//    @Test
//    fun getProductsList_success() = runBlocking {
//        val productDomainResponse = ApiResponse.Success(TestData.mappedResponseProductList())
//        val productUiMappedResponse = TestData.mappedUiProductList()
//        coEvery { productListUseCase() } returns flowOf(productDomainResponse)
//        coEvery { productListUiMapper.map(any()) } returns productUiMappedResponse.toMutableList()
//        productListViewModel.getProductsList()
//        Assert.assertEquals(
//            productUiMappedResponse,
//            productListViewModel.productListStateFlow.value.data
//        )
//    }

//    @Test
//    fun getProductsList_fail() = runBlocking {
//        val errorMsg = "Internal Server Error"
//        coEvery { productListUseCase() } returns flowOf(ApiResponse.Error(errorMsg))
//        productListViewModel.getProductsList()
//        Assert.assertEquals(
//            errorMsg,
//            productListViewModel.productListStateFlow.value.message
//        )
//    }
}