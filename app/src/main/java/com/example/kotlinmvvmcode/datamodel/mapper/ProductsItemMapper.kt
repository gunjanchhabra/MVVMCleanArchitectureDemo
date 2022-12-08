package com.example.kotlinmvvmcode.datamodel.mapper

import com.example.kotlinmvvmcode.datamodel.data.ProductsItemDataModel
import com.example.kotlinmvvmcode.datamodel.model.ProductItemModel
import javax.inject.Inject

class ProductsItemMapper @Inject constructor(val productsColorMapper: ProductsColorMapper) : Mapper<ProductsItemDataModel, ProductItemModel> {
    override fun mapFromModel(model: ProductsItemDataModel): ProductItemModel {
        return ProductItemModel(model.apiFeaturedImage,
        model.brand,
        model.category,
        model.createdAt,
        model.currency,
        model.description,
        model.id,
        model.imageLink,
        model.name,
        model.price,
        model.priceSign,
        model.productApiUrl,
        model.productColors.map {data -> productsColorMapper.mapFromModel(data)},
        model.productLink,
        model.productType,
        model.rating,
        model.tagList,
        model.updatedAt,
        model.websiteLink)
    }
}