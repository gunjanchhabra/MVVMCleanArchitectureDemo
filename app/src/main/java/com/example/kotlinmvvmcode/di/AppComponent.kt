package com.example.kotlinmvvmcode.di

import android.content.Context
import com.example.kotlinmvvmcode.view.activity.ProductActivity
import com.example.kotlinmvvmcode.view.fragment.ProductDetailFragment
import com.example.kotlinmvvmcode.view.fragment.ProductListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, UseCaseModule::class, RepoModule::class])
interface AppComponent{

    fun inject(mainActivity: ProductActivity)
    fun inject(productListFragment: ProductListFragment)
    fun inject(productDetailFragment: ProductDetailFragment)

   @Component.Factory
    interface FactoryInstance{
        fun createInstance(@BindsInstance context: Context) : AppComponent
    }
}