package com.example.kotlinmvvmcode.domain.model.mapper

import com.example.kotlinmvvmcode.data.mapper.Mapper
import com.example.kotlinmvvmcode.data.model.ProductsItemDataModel
import com.example.kotlinmvvmcode.domain.model.ProductItemDomainModel
import javax.inject.Inject

class ProductsItemMapper @Inject constructor(private val productsColorMapper: ProductsColorMapper) :
    Mapper<ProductsItemDataModel, ProductItemDomainModel> {
    override fun mapFromModel(model: ProductsItemDataModel): ProductItemDomainModel {
        return ProductItemDomainModel(
            model.apiFeaturedImage,
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
            model.productColors.map { data -> productsColorMapper.mapFromModel(data) },
            model.productLink,
            model.productType,
            model.rating,
            model.tagList,
            model.updatedAt,
            model.websiteLink
        )
    }
}
