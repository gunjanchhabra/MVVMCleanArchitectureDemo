package com.example.kotlinmvvmcode.datamodel.mapper

import com.example.kotlinmvvmcode.datamodel.data.ProductColorDataModel
import com.example.kotlinmvvmcode.datamodel.model.ProductColorModel
import javax.inject.Inject

class ProductsColorMapper @Inject constructor() : Mapper<ProductColorDataModel, ProductColorModel> {

    override fun mapFromModel(model: ProductColorDataModel): ProductColorModel{
        return ProductColorModel(model.colourName,model.hexValue)
    }
}