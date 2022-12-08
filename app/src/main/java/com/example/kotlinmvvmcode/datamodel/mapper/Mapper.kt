package com.example.kotlinmvvmcode.datamodel.mapper

interface Mapper<M, D> {
    fun mapFromModel(model : M) : D
}