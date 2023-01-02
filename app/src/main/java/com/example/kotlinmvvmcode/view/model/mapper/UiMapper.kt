package com.example.kotlinmvvmcode.view.model.mapper

interface UiMapper<M, D> {
    fun mapToUi(model: M): D
}
