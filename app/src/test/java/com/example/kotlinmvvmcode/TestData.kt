package com.example.kotlinmvvmcode

import com.example.kotlinmvvmcode.datamodel.data.ProductColorDataModel
import com.example.kotlinmvvmcode.datamodel.data.ProductsItemDataModel
import com.example.kotlinmvvmcode.datamodel.model.ProductColorModel
import com.example.kotlinmvvmcode.datamodel.model.ProductItemModel

object TestData {
    val productItemModel = ProductItemModel(
        "", "", "", "", "",
        "", 0, "", "",
        "", "", "", listOf(), "", "",
        0.0, listOf(), "", ""
    )

    val productsItemDataModel = ProductsItemDataModel(
        "", "", "", "", "",
        "", 0, "", "",
        "", "", "", listOf(), "", "",
        0.0, listOf(), "", ""
    )

    val productsColorModel = ProductColorModel("","")
    val productColorDataModel = ProductColorDataModel("","")

     fun mappedResponseProductList() = listOf(
        productItemModel
    )

     fun networkResponseProductList() = listOf(
        productsItemDataModel
    )
}