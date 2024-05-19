package com.foodapps.datasource.menu


import com.foodapps.data.datasource.menu.MenuApiDataSource
import com.foodapps.data.datasource.menu.MenuDataSource
import com.foodapps.data.network.model.CheckoutResponse
import com.foodapps.data.network.model.checkout.CheckoutRequestPayload
import com.foodapps.data.network.model.menus.MenuResponse
import com.foodapps.data.network.services.FoodAppApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class MenuApiDataSourceTest {
    @MockK
    lateinit var service: FoodAppApiService

    private lateinit var ds: MenuDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = MenuApiDataSource(service)
    }

    @Test
    fun getMenuDataSource() {
        runTest {
            val mockResponse = mockk<MenuResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val actualResponse = ds.getMenus("makanan")
            coVerify { service.getMenus(any()) }
            assertEquals(actualResponse, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestPayload>(relaxed = true)
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val actualResponse = ds.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(actualResponse, mockResponse)
        }
    }
}
