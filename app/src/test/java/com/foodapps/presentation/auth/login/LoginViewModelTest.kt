package com.foodapps.presentation.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.foodapps.data.repository.UserRepository
import com.foodapps.presentation.login.LoginViewModel
import com.foodapps.tools.MainCoroutineRule
import com.foodapps.tools.getOrAwaitValue
import com.foodapps.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LoginViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(LoginViewModel(repository))
    }

    @Test
    fun doLogin() {
        val email = "binar1@gmail.com"
        val password = "binar123"
        every { repository.doLogin(email, password) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        val result = viewModel.doLogin(email, password).getOrAwaitValue()
        val payload = (result as ResultWrapper.Success).payload
        assertEquals(true, payload)
    }
}
