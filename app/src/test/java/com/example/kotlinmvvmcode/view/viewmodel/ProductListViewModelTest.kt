package com.example.kotlinmvvmcode.view.viewmodel

import androidx.lifecycle.Observer
import com.example.kotlinmvvmcode.Dispatcher
import com.example.kotlinmvvmcode.TestData
import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.domain.usecase.ProductListUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.utils.Status
import com.example.kotlinmvvmcode.view.model.ProductListUiModel
import com.example.kotlinmvvmcode.view.model.mapper.ProductListUiMapper
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.*

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

    @Test
    fun getProductsList_success() = runBlocking {
        val productDomainResponse = ApiResponse.success(TestData.mappedResponseProductList())
        val productUiMappedResponse = TestData.mappedUiProductList()
        coEvery { productListUseCase() } returns flowOf(productDomainResponse)
        coEvery { productListUiMapper.mapFromModel(any()) } returns productUiMappedResponse.first()
        productListViewModel.getProductsList()
        Assert.assertEquals(
            ApiResponse(Status.SUCCESS, productUiMappedResponse, ""),
            productListViewModel.productListStateFlow.value
        )
    }

    @Test
    fun getProductsList_fail() = runBlocking {
        val errorMsg = "Internal Server Error"
        val productDomainResponse = ApiResponse.error(null, errorMsg)
        coEvery { productListUseCase() } returns flowOf(productDomainResponse)
        productListViewModel.getProductsList()
        Assert.assertEquals(
            ApiResponse(Status.ERROR, null, errorMsg),
            productListViewModel.productListStateFlow.value
        )
    }

}