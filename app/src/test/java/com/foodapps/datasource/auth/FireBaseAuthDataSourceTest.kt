package com.foodapps.datasource.auth

import com.foodapps.data.datasource.auth.AuthDataSource
import com.foodapps.data.datasource.auth.FirebaseAuthDataSource
import com.foodapps.data.source.firebase.FirebaseServices
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FireBaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseServices
    private lateinit var authDataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authDataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        runTest {
            val email = "binar1@gmail.com"
            val password = "binar123"
            coEvery { service.doLogin(email, password) } returns true
            val result = authDataSource.doLogin(email, password)
            coVerify { service.doLogin(email, password) }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            val name = "binarian"
            val email = "binar1@gmail.com"
            val password = "binar123"
            coEvery { service.doRegister(name, email, password) } returns true
            val result = authDataSource.doRegister(name, email, password)
            coVerify { service.doRegister(name, email, password) }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            coEvery { service.updateProfile(any()) } returns true
            val result = authDataSource.updateProfile()
            coVerify { service.updateProfile(any()) }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            val password = "binar12345"
            coEvery { service.updatePassword(password) } returns true
            val result = authDataSource.updatePassword(password)
            coVerify { service.updatePassword(password) }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            val email = "binar@gmail.com"
            coEvery { service.updateEmail(email) } returns true
            val result = authDataSource.updateEmail(email)
            coVerify { service.updateEmail(email) }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runTest {
            every { service.requestChangePasswordByEmail() } returns true
            val result = authDataSource.requestChangePasswordByEmail()
            verify { service.requestChangePasswordByEmail() }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            every { service.doLogout() } returns true
            val result = authDataSource.doLogout()
            verify { service.doLogout() }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { service.isLoggedIn() } returns true
            val result = authDataSource.isLoggedIn()
            verify { service.isLoggedIn() }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun getCurrentUser() {
        runTest {
            val user = mockk<FirebaseUser>()
            every { service.getCurrentUser() } returns user
            every { user.uid } answers { "id" }
            every { user.displayName } answers { "binarian" }
            every { user.email } answers { "binar1@gmail.com" }
            every { user.photoUrl } returns mockk(relaxed = true)
            every { user.phoneNumber } returns null

            val result = authDataSource.getCurrentUser()
            verify { service.getCurrentUser() }
            Assert.assertEquals("id", result?.id)
            Assert.assertEquals("binarian", result?.name)
            Assert.assertEquals("binar1@gmail.com", result?.email)

        }
    }
}
