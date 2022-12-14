package com.example.kotlinmvvmcode.data.mapper

import com.example.kotlinmvvmcode.TestData.productItemModel
import com.example.kotlinmvvmcode.TestData.productsItemDataModel
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ProductsItemMapperTest {
    private val testDispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var colorMapper: ProductsColorMapper

    @Before
    fun setup() {
        MockKAnnotations.init(this, true)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun getMapper_successTest() = runTest {
        val input = productsItemDataModel
        val output = productItemModel

        val mapper = spyk(ProductsItemMapper(colorMapper))

        val result = mapper.mapToDomain(input)

        assertEquals(result, output)

        coVerify(atMost = 1) { mapper.mapToDomain(input) }

        confirmVerified(mapper)
    }
}
