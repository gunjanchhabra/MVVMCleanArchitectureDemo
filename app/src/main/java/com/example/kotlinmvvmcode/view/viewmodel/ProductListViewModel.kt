package com.example.kotlinmvvmcode.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvmcode.datamodel.model.ProductItemModel
import com.example.kotlinmvvmcode.domain.usecase.ProductUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(val productListUseCase: ProductUseCase) :
    ViewModel() {

    private val productListStateFlow : MutableStateFlow<ApiResponse<MutableList<ProductItemModel>>> = MutableStateFlow(
        ApiResponse.loading(null)
    )

    val _productListStateFlow : StateFlow<ApiResponse<MutableList<ProductItemModel>>> = productListStateFlow

    fun getProductsList() {
        viewModelScope.launch {
            productListUseCase.fetchProducts().catch {
                productListStateFlow.value = ApiResponse(Status.ERROR, null, it.message)
            }.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        productListStateFlow.value = ApiResponse(Status.SUCCESS, it.data?.toMutableList(), "")
                    }
                    Status.ERROR -> {
                        productListStateFlow.value = ApiResponse(Status.ERROR, null, it.message)
                    }
                    Status.LOADING -> {
                        productListStateFlow.value = ApiResponse(Status.LOADING, null, null)
                    }
                }
            }
        }
    }
}