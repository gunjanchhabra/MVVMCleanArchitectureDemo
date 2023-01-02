package com.example.kotlinmvvmcode.data.model

import com.google.gson.annotations.SerializedName


data class ProductColorDataModel(
    @SerializedName("colour_name") val colourName: String,
    @SerializedName("hex_value") val hexValue: String
)
