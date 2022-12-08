package com.example.kotlinmvvmcode.di

import com.example.kotlinmvvmcode.domain.repository.ProductRepo
import com.example.kotlinmvvmcode.domain.repository.ProductRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {
        @Provides
        @Singleton
        fun provideRepo(productListRepoImpl: ProductRepoImpl): ProductRepo {
            return productListRepoImpl
        }
}