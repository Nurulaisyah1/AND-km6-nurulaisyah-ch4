package com.foodapps.presentation.detailmenu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.foodapps.R
import com.foodapps.data.datasource.cart.CartDataSource
import com.foodapps.data.datasource.cart.CartDatabaseDataSource
import com.foodapps.data.model.Menu
import com.foodapps.data.repository.CartRepository
import com.foodapps.data.repository.CartRepositoryImpl
import com.foodapps.data.source.local.database.AppDatabase
import com.foodapps.databinding.ActivityDetailMenuBinding
import com.foodapps.presentation.cart.isUserLoggedIn
import com.foodapps.utils.GenericViewModelFactory
import com.foodapps.utils.proceedWhen
import com.foodapps.utils.toDollarFormat
import com.google.firebase.auth.FirebaseAuth

class DetailMenuActivity : AppCompatActivity() {
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

    private val viewModel: DetailMenuViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(
            DetailMenuViewModel(intent?.extras, rp),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        bindMenu(viewModel.menu)
        setClickListener()
        observeData()
    }

    private fun setClickListener() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.ivMinus.setOnClickListener {
            viewModel.minus()
        }
        binding.ivPlus.setOnClickListener {
            viewModel.add()
        }

        binding.btnAddToCart.setOnClickListener {
            if (isUserLoggedIn()) {
                addMenuToCart()
            } else {
                Toast.makeText(this, "Harus login dulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addMenuToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success),
                        Toast.LENGTH_SHORT,
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, getString(R.string.add_to_cart_failed), Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                },
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

    private fun userIsLoggedIn(): Boolean {
        // Check if the current user is authenticated
        return auth.currentUser != null
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.btnAddToCart.isEnabled = it != 0.0
            binding.tvCalculatedMenuPrice.text = it.toDollarFormat()
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.tvMenuCount.text = it.toString()
        }
    }

    companion object {
        const val EXRA_MENU = "EXRA_MENU"

        fun startActivity(
            context: Context,
            menu: Menu,
        ) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXRA_MENU, menu)
            context.startActivity(intent)
        }
    }
}
