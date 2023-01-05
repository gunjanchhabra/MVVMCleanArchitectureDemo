package com.example.kotlinmvvmcode.data.mapper

interface Mapper<M, D> {
    fun mapToDomain(model: M): D
}
