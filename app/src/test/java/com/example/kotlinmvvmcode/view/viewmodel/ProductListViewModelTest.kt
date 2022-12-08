package com.example.kotlinmvvmcode.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.kotlinmvvmcode.Dispatcher
import com.example.kotlinmvvmcode.datamodel.model.ProductItemModel
import com.example.kotlinmvvmcode.domain.usecase.ProductUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.utils.Status
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductListViewModelTest{

    lateinit var productListViewModel: ProductListViewModel

    @RelaxedMockK
    lateinit var productListUseCase: ProductUseCase

    @get:Rule
    var dispatcher = Dispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this,true)
        productListViewModel = ProductListViewModel(productListUseCase)
    }

    @Test
    fun getProductsList_success() {

        val productItemList : List<ProductItemModel> = listOf()
        val productResponse = ApiResponse.success(productItemList)
        coEvery { productListUseCase.fetchProducts() } returns flowOf(productResponse)
        runBlocking {
            productListViewModel.getProductsList()
            Assert.assertEquals(
                ApiResponse(Status.SUCCESS,productItemList, ""),productListViewModel._productListStateFlow.value
            )
        }
    }

    @Test
    fun getProductsList_fail() {
        val errorMsg = "Internal Server Error"
        val productResponse = ApiResponse.error(null, errorMsg)
        coEvery { productListUseCase.fetchProducts() } returns flowOf(productResponse)
        runBlocking {
            productListViewModel.getProductsList()
            Assert.assertEquals(
                ApiResponse(Status.ERROR,null, errorMsg),productListViewModel._productListStateFlow.value
            )
        }
    }
}