package com.example.kotlinmvvmcode.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvmcode.domain.usecase.ProductListUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.utils.Status
import com.example.kotlinmvvmcode.view.model.ProductListUiModel
import com.example.kotlinmvvmcode.view.model.mapper.ProductListUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListViewModel @Inject constructor(private val productListUseCase: ProductListUseCase, private val productListMapper: ProductListUiMapper) :
    ViewModel() {

    private val _productListStateFlow : MutableStateFlow<ApiResponse<MutableList<ProductListUiModel>>> = MutableStateFlow(
        ApiResponse.loading(null)
    )

    val productListStateFlow : StateFlow<ApiResponse<MutableList<ProductListUiModel>>> = _productListStateFlow

    fun getProductsList() {
        viewModelScope.launch {
            productListUseCase().catch {
                _productListStateFlow.value = ApiResponse(Status.ERROR, null, it.message)
            }.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        _productListStateFlow.value = ApiResponse(Status.SUCCESS,
                            it.data?.map { productItemModel -> productListMapper.mapFromModel(productItemModel) }?.toMutableList(), "")
                    }
                    Status.ERROR -> {
                        _productListStateFlow.value = ApiResponse(Status.ERROR, null, it.message)
                    }
                    Status.LOADING -> {
                        _productListStateFlow.value = ApiResponse(Status.LOADING, null, null)
                    }
                }
            }
        }
    }
}