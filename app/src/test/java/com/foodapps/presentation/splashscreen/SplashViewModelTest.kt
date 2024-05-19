package  com.foodapps.presentation.splashscreen
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.foodapps.data.repository.UserRepository
import com.foodapps.tools.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SplashViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repository: UserRepository

    private lateinit var viewModel: SplashViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(SplashViewModel(repository))
    }

    @Test
    fun isLoggedIn() {
        val isLoggedIn = true
        every { repository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isLoggedIn()
        assertEquals(isLoggedIn, result)
    }
}
