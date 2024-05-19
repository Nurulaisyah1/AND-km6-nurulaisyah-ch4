package com.foodapps.datasource.user

import com.foodapps.data.datasource.user.UserDataSource
import com.foodapps.data.datasource.user.UserDataSourceImpl
import com.foodapps.data.source.local.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PrefDataSourceImplTest {
    @MockK
    lateinit var userPreferences: UserPreference
    private lateinit var prefDataSource: UserDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        prefDataSource = UserDataSourceImpl(userPreferences)
    }

    @Test
    fun isUsingDarkMode() {
        runTest {
            every { userPreferences.isUsingDarkMode() } returns true
            val result = prefDataSource.isUsingDarkMode()
            verify { userPreferences.isUsingDarkMode() }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun setUsingDarkMode() {
        runTest {
            val setUsingDarkMode = true
            every { userPreferences.setUsingDarkMode(setUsingDarkMode) } returns Unit
            val result = prefDataSource.setUsingDarkMode(setUsingDarkMode)
            verify { userPreferences.setUsingDarkMode(setUsingDarkMode) }
            Assert.assertEquals(Unit, result)
        }
    }
}