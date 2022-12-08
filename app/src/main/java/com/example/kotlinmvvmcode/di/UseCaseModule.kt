package com.example.kotlinmvvmcode.di

import com.example.kotlinmvvmcode.domain.usecase.ProductUseCase
import com.example.kotlinmvvmcode.domain.usecase.ProductUseCaseImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideUseCase(productListUseCaseImpl: ProductUseCaseImpl): ProductUseCase {
         return productListUseCaseImpl
    }
}