package com.example.kotlinmvvmcode.view.model.mapper

import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.view.model.ProductListUiModel
import javax.inject.Inject

class ProductListUiMapper @Inject constructor() :
    UiMapper<ProductItemDomainModel, ProductListUiModel> {
    override infix fun mapToUi(model: ProductItemDomainModel): ProductListUiModel {
        return ProductListUiModel(
            "https:${model.apiFeaturedImage}",
            model.description,
            model.id,
            model.name,
            "Price- ${model.priceSign}${model.price}"
        )
    }

    infix fun map(model: List<ProductItemDomainModel>): MutableList<ProductListUiModel> =
        model.map { this mapToUi it }.toMutableList()
}
