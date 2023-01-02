package com.example.kotlinmvvmcode.view.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvmcode.domain.usecase.ProductListUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.view.model.ProductListUiModel
import com.example.kotlinmvvmcode.view.model.mapper.ProductListUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val productListUseCase: ProductListUseCase,
    private val productListMapper: ProductListUiMapper
) : ViewModel() {

    private val _productListStateFlow =
        MutableStateFlow<ApiResponse<MutableList<ProductListUiModel>?>>(ApiResponse.Loading())

    val productListStateFlow: StateFlow<ApiResponse<MutableList<ProductListUiModel>?>> =
        _productListStateFlow

    init {
        getProductsList()
    }

    fun getProductsList() {
        viewModelScope.launch {
            productListUseCase().collect { result ->
                when (result) {
                    is ApiResponse.Loading ->
                        _productListStateFlow.value = ApiResponse.Loading()
                    is ApiResponse.Success ->
                        _productListStateFlow.value =
                            ApiResponse.Success(result.data?.let { productListDomain ->
                                (productListMapper map productListDomain)
                            })
                    is ApiResponse.Error ->
                        _productListStateFlow.value = ApiResponse.Error(result.message)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("cleared","cleared")
    }
}

