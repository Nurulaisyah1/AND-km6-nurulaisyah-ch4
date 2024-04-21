package com.foodapps.presentation.DetailMenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.foodapps.R
import com.foodapps.data.model.Menu
import com.foodapps.data.repository.CartRepository
import com.foodapps.data.repository.CartRepositoryImpl
import com.foodapps.data.source.local.database.dao.CartDao
import com.foodapps.databinding.ActivityDetailMenuBinding
import com.foodapps.utils.GenericViewModelFactory
import com.foodapps.utils.proceedWhen
import com.foodapps.utils.toDollarFormat
import java.io.Serializable

class DetailMenuActivity : AppCompatActivity() {

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val cartDao: CartDao = db.cartDao() // Gunakan CartDao di sini
        val rp: CartRepository = CartRepositoryImpl(cartDao) // Gunakan CartDao di sini
        GenericViewModelFactory.create(
            DetailMenuViewModel(intent?.extras, rp)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(viewModel.menu)
        setClickListener()
        observeData()
        setRestaurantLocation()
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
            addMenuToCart()
        }
    }

    private fun addMenuToCart() {
        viewModel.addToCart().observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success), Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT)
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
            binding.tvMenuPrice.text = item.price.toDollarFormat()
            binding.tvMenuDescription.text = item.description // Tampilkan deskripsi menu
        }
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

    private fun setRestaurantLocation() {
        binding.ivLocationIcon.setOnClickListener {
            val gmmIntentUri = Uri.parse("https://g.co/kgs/9bLEzhA")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            }
        }
    }

    companion object {
        const val EXTRA_MENU = "EXTRA_MENU"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRA_MENU, menu as Serializable)
            intent.putExtra(EXTRA_MENU, menu as Parcelable)
            context.startActivity(intent)
        }
    }
}
