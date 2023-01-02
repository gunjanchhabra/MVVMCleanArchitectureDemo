package com.example.kotlinmvvmcode.di

import com.example.kotlinmvvmcode.data.repository.ProductRepo
import com.example.kotlinmvvmcode.data.repository.ProductRepoImpl
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
