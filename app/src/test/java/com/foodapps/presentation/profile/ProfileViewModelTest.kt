package com.foodapps.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.foodapps.data.model.User
import com.foodapps.data.repository.UserRepository
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

class ProfileViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(repository))
    }

    @Test
    fun changeEditMode() {
        val enabledEdit = true
        viewModel.changeEditMode()
        val enabledEditValue = viewModel.isEnableOrDisableEdit.value
        assertEquals(enabledEdit, enabledEditValue)

        val disabledEdit = false
        viewModel.changeEditMode()
        val disabledEditValue = viewModel.isEnableOrDisableEdit.value
        assertEquals(disabledEdit, disabledEditValue)
    }

    @Test
    fun getCurrentUser() {
        val user =
            User(
                "1",
                "Binarian",
                "binar1@gmail.com",
            )
        every { repository.getCurrentUser() } returns user
        val result = viewModel.getCurrentUser()
        assertEquals(user, result)
    }

    @Test
    fun doChangeProfile() {
        val name = "User Binarian"
        every { repository.updateProfile(name) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        val result = viewModel.doChangeProfile(name).getOrAwaitValue()
        val payload = (result as ResultWrapper.Success).payload
        assertEquals(true, payload)
    }

    @Test
    fun doChangePasswordByEmail() {
        val doChangePasswordByEmail = true
        every { repository.requestChangePasswordByEmail() } returns doChangePasswordByEmail
        val result = viewModel.doChangePasswordByEmail()
        assertEquals(doChangePasswordByEmail, result)
    }

    @Test
    fun doLogout() {
        val doLogout = true
        every { repository.doLogout() } returns doLogout
        val result = viewModel.doLogout()
        assertEquals(doLogout, result)
    }
}
