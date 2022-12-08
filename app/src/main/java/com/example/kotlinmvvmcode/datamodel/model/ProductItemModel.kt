package com.example.kotlinmvvmcode.datamodel.model


data class ProductItemModel(
    var apiFeaturedImage: String ? = "",
    val brand: String ? = "",
    val category: String ?= "",
    val createdAt: String ? = "",
    val currency: String ? = "",
    val description: String ? = "",
    val id: Int,
    val imageLink: String ? = "",
    val name: String ? = "",
    val price: String ? = "",
    val priceSign: String ? = "",
    val productApiUrl: String ? = "",
    val productColors: List<ProductColorModel>,
    val productLink: String ? = "",
    val productType: String? = "",
    val rating: Double ? = 0.0,
    val tagList: List<String>,
    val updatedAt: String? = "",
    val websiteLink: String? = ""
)

