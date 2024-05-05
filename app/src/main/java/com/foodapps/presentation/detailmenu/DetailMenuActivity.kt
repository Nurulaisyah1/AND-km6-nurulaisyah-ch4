package com.foodapps.presentation.detailmenu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.foodapps.R
import com.foodapps.data.model.Menu
import com.foodapps.databinding.ActivityDetailMenuBinding
import com.foodapps.presentation.auth.login.LoginActivity
import com.foodapps.utils.proceedWhen
import com.foodapps.utils.toDollarFormat
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMenuActivity : AppCompatActivity() {
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

    private val detailViewModel: DetailMenuViewModel by viewModel {
        parametersOf(intent.extras)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        bindMenu(detailViewModel.menu)
        setClickListener()
        observeData()
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.ivMinus.setOnClickListener {
            detailViewModel.minus()
        }
        binding.ivPlus.setOnClickListener {
            detailViewModel.add()
        }

        binding.btnAddToCart.setOnClickListener {
            if (!isUserLoggedIn()) {
                addMenuToCart()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun addMenuToCart() {
        detailViewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success), Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, getString(R.string.add_to_cart_failed), Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let { item ->
            binding.ivMenuImage.load(item.imgUrl) {
                crossfade(true)
            }
            binding.tvMenuName.text = item.name
            binding.tvMenuDescription.text = item.detail
            binding.tvMenuPrice.text = item.price.toDollarFormat()
            binding.tvLocation.text = item.address
        }
    }

    private fun isUserLoggedIn(): Boolean {
        // Check if the current user is authenticated
        return auth.currentUser != null
    }

    private fun observeData() {
        detailViewModel.priceLiveData.observe(this) {
            binding.btnAddToCart.isEnabled = it != 0.0
            binding.tvCalculatedMenuPrice.text = it.toDollarFormat()
        }
        detailViewModel.menuCountLiveData.observe(this) {
            binding.tvMenuCount.text = it.toString()
        }
    }

    companion object {
        const val EXTRA_ITEM = "EXTRA_ITEM"
    }
}

