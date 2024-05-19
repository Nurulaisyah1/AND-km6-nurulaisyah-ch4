package com.foodapps.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.foodapps.data.model.User
import com.foodapps.data.repository.CategoryRepository
import com.foodapps.data.repository.MenuRepository
import com.foodapps.data.repository.PrefRepository
import com.foodapps.data.repository.UserRepository
import com.foodapps.tools.MainCoroutineRule
import com.foodapps.tools.getOrAwaitValue
import com.foodapps.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    @MockK
    lateinit var prefRepository: PrefRepository

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { prefRepository.isUsingGridMode() } returns true
        every { prefRepository.setUsingGridMode(any()) } returns Unit
        viewModel =
            spyk(
                HomeViewModel(
                    categoryRepository,
                    menuRepository,
                    prefRepository,
                    userRepository,
                ),
            )
    }

    @Test
    fun `isUsingDarkMode should return true`() =
        runTest {
            val expectedValue = true
            val actualValue = viewModel.isUsingGridMode.value
            assertEquals(expectedValue, actualValue)
        }

    @Test
    fun getMenuList() {
        every { menuRepository.getMenus() } returns
            flow {
                emit(
                    ResultWrapper.Success(listOf(mockk(relaxed = true), mockk(relaxed = true))),
                )
            }
        val result = viewModel.getMenus().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun getCategoryList() {
        every { categoryRepository.getCategories() } returns
            flow {
                emit(
                    ResultWrapper.Success(listOf(mockk(relaxed = true), mockk(relaxed = true))),
                )
            }
        val result = viewModel.getCategoryList().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
    }

    @Test
    fun changeListMode() {
        runTest {
            val currentValue = viewModel.isUsingGridMode.value ?: false
            val expectedNewValue = !currentValue
            viewModel.changeListMode()
            assertEquals(expectedNewValue, viewModel.isUsingGridMode.value)
        }
    }

    @Test
    fun getUser() {
        val user =
            User(
                "1",
                "binarian",
                "binar1@gmail.com",

            )
        // runTest {
        every { userRepository.getCurrentUser() } returns user
        val result = viewModel.getUser()
        assertEquals(user, result)
        // }
    }

    @Test
    fun isLoggedIn() {
        val isLoggedIn = true
        every { userRepository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isLoggedIn()
        assertEquals(isLoggedIn, result)
    }
}
