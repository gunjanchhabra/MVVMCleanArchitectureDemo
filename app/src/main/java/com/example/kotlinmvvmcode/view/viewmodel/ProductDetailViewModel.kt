package com.example.kotlinmvvmcode.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinmvvmcode.domain.usecase.ProductDetailUseCase
import com.example.kotlinmvvmcode.utils.ApiResponse
import com.example.kotlinmvvmcode.utils.Status
import com.example.kotlinmvvmcode.view.model.ProductListUiModel
import com.example.kotlinmvvmcode.view.model.mapper.ProductListUiMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(val productDetailUseCase: ProductDetailUseCase, private val productListMapper: ProductListUiMapper)  : ViewModel(){

    private val _productDetailStateFlow : MutableStateFlow<ApiResponse<ProductListUiModel>> = MutableStateFlow(
        ApiResponse.loading(null)
    )
    val productDetailStateFlow : StateFlow<ApiResponse<ProductListUiModel>> = _productDetailStateFlow


    fun getDetailOfProduct( id : Int) {
        viewModelScope.launch {
            productDetailUseCase(id).catch {
                ApiResponse(Status.ERROR, null, it.message)
            }.collect{
                when (it.status) {
                    Status.SUCCESS -> {
                        _productDetailStateFlow.value = ApiResponse(Status.SUCCESS,
                            it.data?.let { productItemModel -> productListMapper.mapFromModel(productItemModel) }, "")
                    }
                    Status.ERROR -> {
                        _productDetailStateFlow.value = ApiResponse(Status.ERROR, null, it.message)
                    }
                    Status.LOADING -> {
                        _productDetailStateFlow.value = ApiResponse(Status.LOADING, null, null)
                    }
                }
            }
        }
    }

    fun getProductImageUrl(apiFeaturedImage: String?): Any {
         return "https:${apiFeaturedImage}"
    }
}