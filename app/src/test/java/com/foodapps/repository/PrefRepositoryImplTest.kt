package com.foodapps.repository

import com.foodapps.data.repository.PrefRepository
import com.foodapps.data.repository.PrefRepositoryImpl
import com.foodapps.data.source.local.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class PrefRepositoryImplTest {
    @MockK
    lateinit var userPreference: UserPreference
    private lateinit var repository: PrefRepository

    private lateinit var repositoryImpl: PrefRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repositoryImpl = PrefRepositoryImpl(userPreference)

    }

    @Test
    fun isUsingGridMode() {
        runTest {
            every { repository.isUsingGridMode() } returns true
            val result = userPreference.isUsingGridMode()
            verify { repository.isUsingGridMode() }
            assertEquals(true, result)
        }
    }

    @Test
    fun setUsingGridMode() {
        runTest {
            val seUsingGridMode = true
            every { repository.setUsingGridMode(seUsingGridMode) } returns Unit
            val result = userPreference.setUsingGridMode(seUsingGridMode) // Use userPreference instead of dataSource
            verify { repository.setUsingGridMode(seUsingGridMode) }
            assertEquals(Unit, result)
        }
    }
}
