package com.example.kotlinmvvmcode.data.mapper

interface Mapper<M, D> {
    fun mapFromModel(model: M): D
}
