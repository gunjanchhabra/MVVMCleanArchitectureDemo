package com.example.kotlinmvvmcode

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class Dispatcher(private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())): TestWatcher(){

    override fun starting(description: Description) = Dispatchers.setMain(dispatcher)

    override fun finished(description: Description) = Dispatchers.resetMain()

}