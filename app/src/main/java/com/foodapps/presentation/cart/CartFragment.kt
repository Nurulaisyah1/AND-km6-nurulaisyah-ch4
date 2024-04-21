package com.foodapps.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.foodapps.R
import com.foodapps.data.model.Cart
import com.foodapps.data.repository.CartRepositoryImpl
import com.foodapps.data.source.local.database.CartDatabase
import com.foodapps.databinding.FragmentCartBinding
import com.foodapps.presentation.checkout.CheckoutActivity
import com.foodapps.presentation.common.adapter.CartListAdapter
import com.foodapps.presentation.common.adapter.CartListener
import com.foodapps.presentation.login.LoginActivity
import com.foodapps.utils.GenericViewModelFactory
import com.foodapps.utils.hideKeyboard
import com.foodapps.utils.toDollarFormat

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var loginButton: Button

    private val dao = CartDatabase.getInstance(requireContext()).cartDao()
    private val repository = CartRepositoryImpl(dao)

    private val viewModel: CartViewModel by viewModels {
        GenericViewModelFactory.create(CartViewModel(repository))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(object : CartListener {
            override fun onPlusTotalItemCartClicked(cart: Cart) {
                viewModel.increaseCart(cart)
            }

            override fun onMinusTotalItemCartClicked(cart: Cart) {
                viewModel.decreaseCart(cart)
            }

            override fun onRemoveCartClicked(cart: Cart) {
                viewModel.removeCart(cart)
            }

            override fun onUserDoneEditingNotes(cart: Cart) {
                viewModel.setCartNotes(cart)
                hideKeyboard()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        loginButton = binding.root.findViewById(R.id.loginButton)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        setClickListeners()
        checkLoginStatus()
    }

    private fun setClickListeners() {
        binding.btnCheckout.setOnClickListener {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }

        loginButton.setOnClickListener {
            val loginIntent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun checkLoginStatus() {
        if (viewModel.isUserLoggedIn()) {
            // Show actions to add food items if user is logged in
        } else {
            // Hide actions to add food items if user is not logged in
            loginButton.visibility = View.VISIBLE
        }
    }

    private fun showLoadingState() {
        binding.layoutState.root.isVisible = true
        binding.layoutState.pbLoading.isVisible = true
        binding.layoutState.tvError.isVisible = false
        binding.rvCart.isVisible = false
    }

    private fun showCartItems(menus: List<Cart>, totalPrice: Double) {
        binding.layoutState.root.isVisible = false
        binding.layoutState.pbLoading.isVisible = false
        binding.layoutState.tvError.isVisible = false
        binding.rvCart.isVisible = true

        adapter.submitData(menus)
        binding.tvTotalPrice.text = totalPrice.toDollarFormat()
    }

    private fun showErrorState(errorMessage: String?) {
        binding.layoutState.root.isVisible = true
        binding.layoutState.pbLoading.isVisible = false
        binding.layoutState.tvError.isVisible = true
        binding.layoutState.tvError.text = errorMessage ?: getString(R.string.text_generic_error)
        binding.rvCart.isVisible = false
    }

    private fun showEmptyState() {
        binding.layoutState.root.isVisible = true
        binding.layoutState.pbLoading.isVisible = false
        binding.layoutState.tvError.isVisible = true
        binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
        binding.rvCart.isVisible = false
    }

    private fun setupList() {
        binding.rvCart.adapter = adapter
    }
}
