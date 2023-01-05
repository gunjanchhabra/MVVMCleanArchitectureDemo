package com.example.kotlinmvvmcode.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvmcode.domain.usecase.ProductDetailUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.view.model.ProductListUiModel
import com.example.kotlinmvvmcode.view.model.mapper.ProductListUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(
    private val productDetailUseCase: ProductDetailUseCase,
    private val productListMapper: ProductListUiMapper
) : ViewModel() {

    private val _productDetailStateFlow =
        MutableStateFlow<ApiResponse<ProductListUiModel>>(ApiResponse.Loading)
    val productDetailStateFlow: StateFlow<ApiResponse<ProductListUiModel>> =
        _productDetailStateFlow

    fun getDetailOfProduct(id: Int) {
        viewModelScope.launch {
            productDetailUseCase(id).collect { result ->
                when (result) {
                    is ApiResponse.Success -> {
                        _productDetailStateFlow.value =
                            ApiResponse.Success(productListMapper mapToUi result.data)
                    }
                    is ApiResponse.Error -> {
                        _productDetailStateFlow.value = ApiResponse.Error(result.message)
                    }
                    is ApiResponse.Loading -> {
                        _productDetailStateFlow.value = ApiResponse.Loading
                    }
                }
            }
        }
    }
}
