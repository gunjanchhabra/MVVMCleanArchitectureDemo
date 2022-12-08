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

class ProductDetailViewModel @Inject constructor(val productListUseCase: ProductUseCase)  : ViewModel(){

    private val productDetailStateFlow : MutableStateFlow<ApiResponse<ProductItemModel>> = MutableStateFlow(
        ApiResponse.loading(null)
    )
    val _productDetailStateFlow : StateFlow<ApiResponse<ProductItemModel>> = productDetailStateFlow


    fun getDetailOfProduct( id : Int) {
        viewModelScope.launch {
            productListUseCase.fetchProductDetail(id).catch {
                ApiResponse(Status.ERROR, null, it.message)
            }.collect{
                when (it.status) {
                    Status.SUCCESS -> {
                        productDetailStateFlow.value = ApiResponse(Status.SUCCESS, it.data, "")
                    }
                    Status.ERROR -> {
                        productDetailStateFlow.value = ApiResponse(Status.ERROR, null, it.message)
                    }
                    Status.LOADING -> {
                        productDetailStateFlow.value = ApiResponse(Status.LOADING, null, null)
                    }
                }
            }
        }
    }

    fun getProductImageUrl(apiFeaturedImage: String?): Any {
         return "https:${apiFeaturedImage}"
    }
}