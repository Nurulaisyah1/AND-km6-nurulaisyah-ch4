package com.foodapps.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.foodapps.data.model.Category
import com.foodapps.data.model.Menu
import com.foodapps.databinding.FragmentHomeBinding
import com.foodapps.presentation.DetailMenu.DetailMenuActivity
import com.foodapps.presentation.home.adapter.CategoryListAdapter
import com.foodapps.presentation.home.adapter.MenuListAdapter
import com.foodapps.utils.GridSpacingItemDecoration
import com.foodapps.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {
            getCategoryData(it.slug)
        }
    }

    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter(mutableListOf()) { menu ->
            DetailMenuActivity.startActivity(requireContext(), menu)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListMenu()
        setupListCategory()
        getCategoryData() // Fetch category data when fragment is created
        getMenuData(null) // Fetch menu data with null slug initially
    }

    private fun getCategoryData() {
        viewModel.getCategories().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = { categoryList ->
                    categoryList.payload?.let { bindCategoryList(it) }
                },
                doOnError = { error ->
                    Toast.makeText(requireContext(), "Failed to fetch category list: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun getMenuData(slug: String?) {
        viewModel.getMenusByCategory(slug).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = { menuList ->
                    bindMenuList(menuList)
                },
                doOnError = { error ->
                    Toast.makeText(requireContext(), "Failed to fetch menu list: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun setupListCategory() {
        binding.rvCategory.adapter = categoryAdapter
    }

    private fun setupListMenu() {
        val itemDecoration = GridSpacingItemDecoration(2, 12, true)
        binding.rvProductList.adapter = menuAdapter
        binding.rvProductList.addItemDecoration(itemDecoration)
    }

    private fun bindCategoryList(data: List<Category>) {
        categoryAdapter.submitData(data)
    }

    private fun bindMenuList(data: List<Menu>) {
        menuAdapter.submitData(data)
    }
}
