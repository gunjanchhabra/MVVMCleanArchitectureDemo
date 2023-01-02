package com.example.kotlinmvvmcode

import com.example.kotlinmvvmcode.data.model.ProductColorDataModel
import com.example.kotlinmvvmcode.data.model.ProductsItemDataModel
import com.example.kotlinmvvmcode.domain.model.ProductColorDomainModel
import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.view.model.ProductListUiModel

object TestData {
    val productItemModel = ProductItemDomainModel(
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

    val productItemUiModel = ProductListUiModel(
        "", "", 0, "", ""
    )

    val productsColorModel = ProductColorDomainModel("", "")
    val productColorDataModel = ProductColorDataModel("", "")

    fun mappedResponseProductList() = listOf(
        productItemModel
    )

    fun networkResponseProductList() = listOf(
        productsItemDataModel
    )

    fun mappedUiProductList() = listOf(productItemUiModel)
}
