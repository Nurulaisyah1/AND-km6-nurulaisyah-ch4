package com.foodapps.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.foodapps.data.repository.CartRepository
import com.foodapps.data.repository.MenuRepository
import com.foodapps.data.repository.UserRepository
import com.foodapps.tools.MainCoroutineRule
import com.foodapps.tools.getOrAwaitValue
import com.foodapps.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CheckoutViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { cartRepository.getCheckoutData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Triple(
                            mockk(relaxed = true), mockk(relaxed = true), 0.0,
                        ),
                    ),
                )
            }

        viewModel =
            spyk(
                CheckoutViewModel(
                    cartRepository,
                    userRepository,
                    menuRepository,
                ),
            )
    }

    @Test
    fun deleteAllCart() {
        every { cartRepository.deleteAll() } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.deleteAllCart()
        // verify { cartRepository.deleteAll() }
    }

    @Test
    fun checkoutCart() {
        val mockUsername = "hitler"
        every { userRepository.getCurrentUser()?.name } returns mockUsername
        val expected = ResultWrapper.Success(true)
        coEvery { menuRepository.createOrder(any()) } returns
            flow {
                emit(expected)
            }
        val result = viewModel.checkoutCart().getOrAwaitValue()
        assertEquals(expected, result)
    }

    @Test
    fun isLoggedIn() {
        val isLoggedIn = true
        every { userRepository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isLoggedIn()
        assertEquals(isLoggedIn, result)
    }
}
