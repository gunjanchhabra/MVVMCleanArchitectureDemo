package com.example.kotlinmvvmcode.domain.model.mapper

import com.example.kotlinmvvmcode.data.mapper.Mapper
import com.example.kotlinmvvmcode.data.model.ProductColorDataModel
import com.example.kotlinmvvmcode.domain.model.ProductColorDomainModel
import javax.inject.Inject

class ProductsColorMapper @Inject constructor() :
    Mapper<ProductColorDataModel, ProductColorDomainModel> {

    override fun mapFromModel(model: ProductColorDataModel): ProductColorDomainModel {
        return ProductColorDomainModel(model.colourName, model.hexValue)
    }
}
