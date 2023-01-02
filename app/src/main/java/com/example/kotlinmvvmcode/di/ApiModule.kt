package com.example.kotlinmvvmcode.di

import com.example.kotlinmvvmcode.data.network.ApiService
import com.example.kotlinmvvmcode.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideLoginRetrofitService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        return client.build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}
