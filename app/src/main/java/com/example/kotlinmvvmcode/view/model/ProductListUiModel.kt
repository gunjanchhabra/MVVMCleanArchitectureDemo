package com.example.kotlinmvvmcode.view.model


data class ProductListUiModel(
    var apiFeaturedImage: String? = "",
    val description: String? = "",
    val id: Int,
    val name: String? = "",
    val price: String? = ""
)
