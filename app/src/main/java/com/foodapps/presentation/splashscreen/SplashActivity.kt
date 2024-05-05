package  com.foodapps.presentation.splashscreen
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.foodapps.databinding.ActivitySplashBinding
import com.foodapps.presentation.main.MainActivity
import com.foodapps.utils.GenericViewModelFactory

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels {
        GenericViewModelFactory.create(createViewModel())
    }

    private fun createViewModel(): SplashViewModel {
        return SplashViewModel()
    }

    private val binding: ActivitySplashBinding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        checkIfUserLogin()
    }

    private fun checkIfUserLogin() {
        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
        },1500)
    }


}