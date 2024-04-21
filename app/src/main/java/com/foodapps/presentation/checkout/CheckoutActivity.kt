package com.foodapps.presentation.checkout

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.foodapps.data.repository.CartRepositoryImpl
import com.foodapps.databinding.ActivityCheckoutBinding
import com.foodapps.utils.GenericViewModelFactory
import com.foodapps.utils.proceedWhen

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private val viewModel: CheckoutViewModel by viewModels {
        val cartRepository = CartRepositoryImpl()
        GenericViewModelFactory.create(CheckoutViewModel(cartRepository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        observeData()
    }

    private fun setupViews() {
        binding.btnCheckout.setOnClickListener {
            showCheckoutDialog()
        }
    }

    private fun observeData() {
        viewModel.checkoutResult.observe(this, Observer { result ->
            result.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Checkout berhasil", Toast.LENGTH_SHORT).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, "Gagal melakukan checkout", Toast.LENGTH_SHORT).show()
                }
            )
        })
    }

    private fun showCheckoutDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Konfirmasi Checkout")
            .setMessage("Apakah Anda yakin ingin checkout?")
            .setPositiveButton("Ya") { _, _ ->
                viewModel.checkout()
            }
            .setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }
}
