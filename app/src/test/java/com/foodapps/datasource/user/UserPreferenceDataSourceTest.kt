package com.foodapps.datasource.user


import com.foodapps.data.datasource.pref.PrefDataSource
import com.foodapps.data.datasource.pref.PrefDataSourceImpl
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
    private lateinit var prefDataSource: PrefDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        prefDataSource = PrefDataSourceImpl(userPreferences)
    }

    @Test
    fun isUsingGridMode() {
        runTest {
            every { userPreferences.isUsingGridMode() } returns true
            val result = prefDataSource.isUsingGridMode()
            verify { userPreferences.isUsingGridMode() }
            Assert.assertEquals(true, result)
        }
    }

    @Test
    fun setUsingGridMode() {
        runTest {
            val setUsingGridMode = true
            every { userPreferences.setUsingGridMode(setUsingGridMode) } returns Unit
            val result = prefDataSource.setUsingGridMode(setUsingGridMode)
            verify { userPreferences.setUsingGridMode(setUsingGridMode) }
            Assert.assertEquals(Unit, result)
        }
    }
}