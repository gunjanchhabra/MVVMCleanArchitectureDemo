package com.example.kotlinmvvmcode.data.mapper

import com.example.kotlinmvvmcode.TestData
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductColorModelTest {
    @Test
    fun getMapper_successTest() = runBlocking {
        val input = TestData.productColorDataModel
        val output = TestData.productsColorModel

        val mapper = spyk(ProductsColorMapper())

        val result = mapper.mapToDomain(input)

        assertEquals(result, output)

        coVerify(atMost = 1) { mapper.mapToDomain(input) }

        confirmVerified(mapper)
    }
}
