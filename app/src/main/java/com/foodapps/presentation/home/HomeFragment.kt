package com.foodapps.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodapps.data.datasource.category.CategoryApiDataSource
import com.foodapps.data.datasource.category.CategoryDataSource
import com.foodapps.data.datasource.menu.MenuApiDataSource
import com.foodapps.data.datasource.menu.MenuDataSource
import com.foodapps.data.model.Category
import com.foodapps.data.model.Menu
import com.foodapps.data.network.services.FoodAppApiService
import com.foodapps.data.repository.CategoryRepository
import com.foodapps.data.repository.CategoryRepositoryImpl
import com.foodapps.data.repository.MenuRepository
import com.foodapps.data.repository.MenuRepositoryImpl
import com.foodapps.databinding.FragmentHomeBinding
import com.foodapps.presentation.detailmenu.DetailMenuActivity
import com.foodapps.presentation.home.adapter.CategoryListAdapter
import com.foodapps.presentation.home.adapter.MenuListAdapter
import com.foodapps.presentation.home.utils.LayoutManagerType
import com.foodapps.presentation.login.LoginActivity
import com.foodapps.utils.GenericViewModelFactory
import com.foodapps.utils.GridSpacingItemDecoration
import com.foodapps.utils.Preferences
import com.foodapps.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        val service = FoodAppApiService.invoke()
        val productDataSource: MenuDataSource = MenuApiDataSource(service)
        val productRepository: MenuRepository = MenuRepositoryImpl(productDataSource)
        val categoryDataSource: CategoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, productRepository))
    }

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter { category ->
            getMenuData(category.slug)
        }
    }

    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter(
            mutableListOf(),
            if (Preferences.getLoggedStatus(
                    requireActivity(),
                )
            ) {
                LayoutManagerType.LINEAR_LAYOUT_MANAGER
            } else {
                LayoutManagerType.GRID_LAYOUT_MANAGER
            },
        ) { menu ->
            DetailMenuActivity.startActivity(requireContext(), menu)
        }
    }

    fun switchLayoutManager(layoutManagerType: LayoutManagerType) {
        val layoutManager =
            when (layoutManagerType) {
                LayoutManagerType.LINEAR_LAYOUT_MANAGER -> LinearLayoutManager(requireContext())
                LayoutManagerType.GRID_LAYOUT_MANAGER -> GridLayoutManager(requireContext(), 2) // Sesuaikan jumlah kolom di sini
            }
        binding.rvProductList.layoutManager = layoutManager
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupListMenu()
        setupListCategory()
        getCategoryData() // Fetch category data when fragment is created
        getMenuData(null) // Fetch menu data with null slug initially
        Toast.makeText(requireContext(), "${Preferences.getLoggedStatus(requireActivity())}", Toast.LENGTH_SHORT).show()
        binding.btnSwitch.setOnClickListener {
            menuAdapter.notifyDataSetChanged()
            Preferences.setLoggedStatus(requireActivity(), !Preferences.getLoggedStatus(requireActivity()))

            if (Preferences.getLoggedStatus(requireActivity()))
                {
                    switchLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER)
                } else
                {
                    switchLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER)
                }
        }

        binding.layoutHeader.ivProfileHeaderHome.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

    private fun getCategoryData() {
        viewModel.getCategories().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = { getCategories ->
                    getCategories.payload?.let { bindCategoryList(it) }
                },
                doOnError = { error ->
                    Toast.makeText(
                        requireContext(),
                        "$error ${error.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun getMenuData(slug: String?) {
        viewModel.getMenus(slug).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = { getMenus ->
                    bindMenuList(getMenus.payload.orEmpty())
                },
                doOnError = { error ->
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch menu list: ${error.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                },
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
