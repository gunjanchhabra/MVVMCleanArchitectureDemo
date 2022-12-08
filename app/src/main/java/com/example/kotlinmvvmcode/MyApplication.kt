package com.example.kotlinmvvmcode

import android.app.Application
import com.example.kotlinmvvmcode.di.AppComponent
import com.example.kotlinmvvmcode.di.DaggerAppComponent

class MyApplication : Application() {

    val appComponent : AppComponent by lazy{
        DaggerAppComponent.factory().createInstance(applicationContext)
    }
}