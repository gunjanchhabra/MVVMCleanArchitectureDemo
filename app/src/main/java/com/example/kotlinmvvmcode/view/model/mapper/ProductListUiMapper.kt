package com.example.kotlinmvvmcode.view.model.mapper

import com.example.kotlinmvvmcode.datamodel.mapper.Mapper
import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import com.example.kotlinmvvmcode.view.model.ProductListUiModel
import javax.inject.Inject

class ProductListUiMapper @Inject constructor() : Mapper<ProductItemDomainModel, ProductListUiModel> {
    override fun mapFromModel(model: ProductItemDomainModel): ProductListUiModel {
        return ProductListUiModel(model.apiFeaturedImage,
        model.description,
        model.id,
        model.name,
        model.price,
        model.priceSign)
    }
}