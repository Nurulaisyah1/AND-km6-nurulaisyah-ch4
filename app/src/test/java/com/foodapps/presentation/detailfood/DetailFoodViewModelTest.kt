package com.foodapps.presentation.detailfood

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.foodapps.data.model.Menu
import com.foodapps.data.repository.CartRepository
import com.foodapps.presentation.detailmenu.DetailMenuActivity
import com.foodapps.presentation.detailmenu.DetailMenuViewModel
import com.foodapps.tools.MainCoroutineRule
import com.foodapps.tools.getOrAwaitValue
import com.foodapps.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DetailFoodViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var intent: Bundle

    private lateinit var viewModel: DetailMenuViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        val expectedCatalog =
            Menu(
                "1",
                "das_reich",
                35000.0,
                "Heil fuhrer",
                "Reichstag",
                "sdfsdfsdfdf",

            )
        every { intent.getParcelable<Menu>(DetailMenuActivity.EXRA_MENU) } returns expectedCatalog

        viewModel =
            spyk(
                DetailMenuViewModel(
                    intent, cartRepository,
                ),
            )
    }

    @Test
    fun getMenuCountLiveData() {
        val menuCount = viewModel.menuCountLiveData.getOrAwaitValue()
        assertEquals(0, menuCount)
    }

    @Test
    fun getPriceLiveData() {
        val price = viewModel.priceLiveData.getOrAwaitValue()
        assertEquals(0.0, price, 0.0)
    }

    @Test
    fun addItem() {
        viewModel.addItem()
        assertEquals(1, viewModel.menuCountLiveData.getOrAwaitValue())
        assertEquals(35000.0, viewModel.priceLiveData.getOrAwaitValue(), 0.0)
    }

    @Test
    fun minusItem() {
        viewModel.addItem()
        viewModel.minusItem()
        assertEquals(0, viewModel.menuCountLiveData.getOrAwaitValue())
        assertEquals(0.0, viewModel.priceLiveData.getOrAwaitValue(), 0.0)
    }

    @Test
    fun addToCart() {
        runTest {
            val menu =
                Menu(
                    "1",
                    "das_reich",
                    35000.0,
                    "Heil fuhrer",
                    "Reichstag",
                    "sdfsdfsdfdf",
                )

            coEvery { cartRepository.createCart(menu, any()) } returns
                flow {
                    emit(ResultWrapper.Success(true))
                }

            viewModel.addItem()
            val result = viewModel.addToCart().getOrAwaitValue()

            assertTrue(result is ResultWrapper.Success)
            assertEquals(true, (result as ResultWrapper.Success).payload)
        }
    }
}
