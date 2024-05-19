/*
package com.foodapps.repository

import app.cash.turbine.test
import com.foodapps.data.datasource.menu.MenuDataSource
import com.foodapps.data.model.Cart
import com.foodapps.data.network.model.CheckoutResponse
import com.foodapps.data.network.model.menus.MenuResponse
import com.foodapps.data.repository.MenuRepository
import com.foodapps.data.repository.MenuRepositoryImpl
import com.foodapps.data.source.network.model.menus.MenuItemResponse
import com.foodapps.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {
    @MockK
    lateinit var dataSource: MenuDataSource
    private lateinit var repository: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = MenuRepositoryImpl(dataSource)
    }

    @Test
    fun `when get menu success`() {
        val menu1 =
            MenuItemResponse(
                id = "asadasdsa",
                name = "dfsdfsdf",
                imgUrl  = "fsdfsdfsdf",
                price  = 9.000,
                description  = "adfadfsdfsf",
                address = "asfsdfsdfd",

            )
        val menu2 =
            MenuItemResponse(
                id = "asadasdsa",
                name = "dfsdfsdf",
                imgUrl  = "fsdfsdfsdf",
                price  = 9.000,
                description  = "adfadfsdfsf",
                address = "asfsdfsdfd",
            )
        val mockListCatalog = listOf(menu1, menu2)
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockListCatalog
        runTest {
            coEvery { dataSource.getMenuDataSource(any()) } returns mockResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getMenuDataSource(any()) }
            }
        }
    }

    @Test
    fun `when get menu loading`() {
        val menu1 =
            MenuItemResponse(
                id = "asadasdsa",
                name = "dfsdfsdf",
                imgUrl  = "fsdfsdfsdf",
                price  = 9.000,
                description  = "adfadfsdfsf",
                address = "asfsdfsdfd",
            )
        val menu2 =
            MenuItemResponse(
                id = "asadasdsa",
                name = "dfsdfsdf",
                imgUrl  = "fsdfsdfsdf",
                price  = 9.000,
                description  = "adfadfsdfsf",
                address = "asfsdfsdfd",
            )
        val mockListMenu = listOf(menu1, menu2)
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { dataSource.getMenuDataSource(any()) } returns mockResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getMenuDataSource(any()) }
            }
        }
    }

    @Test
    fun `when get menu error`() {
        runTest {
            coEvery { dataSource.getMenuDataSource(any()) } throws IllegalStateException("Mock error")
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getMenuDataSource(any()) }
            }
        }
    }

    @Test
    fun `when get menu empty`() {
        val mockListMenu = listOf<MenuItemResponse>()
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { dataSource.getMenuDataSource(any()) } returns mockResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getMenuDataSource(any()) }
            }
        }
    }

    @Test
    fun `when create order success`() {
        val mockCart =
            listOf(
                Cart(
                    id = 1,
                    menuId = "asdasdasd",
                    menuName = "sdfsdf",
                    menuImgUrl = "sdfsdfsd",
                    menuPrice = 10000.0,
                    itemQuantity = 2,
                    itemNotes = "dfgdsfgsddfg",
                ),
                Cart(
                    id = 2,
                    menuId = "sdfsadfsadf",
                    menuName = "dfgdfgd",
                    menuImgUrl = "dsfgsadfsa",
                    menuPrice = 20000.0,
                    itemQuantity = 1,
                    itemNotes = "sdfsdfsdf",
                ),
            )
        val totalPrice = 35
        val username = "binarian"
        val mockResponse = mockk<CheckoutResponse>(relaxed = true)
        runTest {
            coEvery { dataSource.createOrder(any()) } returns mockResponse
            repository.createOrder(username, mockCart, totalPrice).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `when create order loading`() {
        val mockCart =
            listOf(
                Cart(
                    id = 1,
                    menuId = "asdasdasd",
                    menuName = "sdfsdf",
                    menuImgUrl = "sdfsdfsd",
                    menuPrice = 10000.0,
                    itemQuantity = 2,
                    itemNotes = "dfgdsfgsddfg",
                ),
                Cart(
                    id = 2,
                    menuId = "sdfsadfsadf",
                    menuName = "dfgdfgd",
                    menuImgUrl = "dsfgsadfsa",
                    menuPrice = 20000.0,
                    itemQuantity = 1,
                    itemNotes = "sdfsdfsdf",
                ),
            )
        val totalPrice = 35
        val username = "binarian"
        val mockResponse = mockk<CheckoutResponse>(relaxed = true)
        runTest {
            coEvery { dataSource.createOrder(any()) } returns mockResponse
            repository.createOrder(username, mockCart, totalPrice).map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }

    @Test
    fun `when create order error`() {
        val mockCart =
            listOf(
                Cart(
                    id = 1,
                    menuId = "asdasdasd",
                    menuName = "sdfsdf",
                    menuImgUrl = "sdfsdfsd",
                    menuPrice = 10000.0,
                    itemQuantity = 2,
                    itemNotes = "dfgdsfgsddfg",
                ),
                Cart(
                    id = 2,
                    menuId = "sdfsadfsadf",
                    menuName = "dfgdfgd",
                    menuImgUrl = "dsfgsadfsa",
                    menuPrice = 20000.0,
                    itemQuantity = 1,
                    itemNotes = "sdfsdfsdf",
                ),
            )
        val totalPrice = 35
        val username = "binarian"
        runTest {
            coEvery { dataSource.createOrder(any()) } throws IllegalStateException("Mock error")
            repository.createOrder(username, mockCart, totalPrice).map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.createOrder(any()) }
            }
        }
    }
}
*/
