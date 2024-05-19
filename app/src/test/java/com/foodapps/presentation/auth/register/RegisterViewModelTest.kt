package com.foodapps.presentation.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.foodapps.data.repository.UserRepository
import com.foodapps.presentation.register.RegisterViewModel
import com.foodapps.tools.MainCoroutineRule
import com.foodapps.utils.ResultWrapper
import com.foodapps.tools.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(RegisterViewModel(repository))
    }

    @Test
    fun doRegister() {
        val name = "Das Reich"
        val email = "hitler@gmail.com"
        val password = "hitler123"
        every { repository.doRegister(name, email, password) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        val result = viewModel.doRegister(name, email, password).getOrAwaitValue()
        val payload = (result as ResultWrapper.Success).payload
        Assert.assertEquals(true, payload)
    }
}
