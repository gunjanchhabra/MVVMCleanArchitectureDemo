package com.example.kotlinmvvmcode.data.network

import com.example.kotlinmvvmcode.data.model.ProductsItemDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    companion object {
        private const val PRODUCT_API_ENDPOINT = "products.json"
    }

    @GET(PRODUCT_API_ENDPOINT)
    suspend fun getProducts(): Response<List<ProductsItemDataModel>>

    @GET("products/{id}.json")
    suspend fun getProductDetail(@Path("id") id: Int): Response<ProductsItemDataModel>
}
