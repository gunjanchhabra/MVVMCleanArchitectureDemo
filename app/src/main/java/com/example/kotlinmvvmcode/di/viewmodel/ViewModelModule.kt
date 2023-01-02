package com.example.kotlinmvvmcode.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinmvvmcode.view.viewmodel.ProductDetailViewModel
import com.example.kotlinmvvmcode.view.viewmodel.ProductListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    internal abstract fun productListViewModel(viewModel: ProductListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductDetailViewModel::class)
    internal abstract fun productDetailViewModel(viewModel: ProductDetailViewModel): ViewModel
}
