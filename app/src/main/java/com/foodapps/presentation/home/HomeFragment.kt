package com.foodapps.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.foodapps.R
import com.foodapps.data.model.Category
import com.foodapps.data.model.Menu
import com.foodapps.databinding.FragmentHomeBinding
import com.foodapps.presentation.auth.login.LoginActivity
import com.foodapps.presentation.detailmenu.DetailMenuActivity
import com.foodapps.presentation.home.adapter.CategoryListAdapter
import com.foodapps.presentation.home.adapter.MenuListAdapter
import com.foodapps.presentation.home.utils.LayoutManagerType
import com.foodapps.utils.GridSpacingItemDecoration
import com.foodapps.utils.Preferences
import com.foodapps.utils.proceedWhen
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var auth: FirebaseAuth
    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter { category ->
            getMenuData(category.slug)
        }
    }

    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter(
            mutableListOf(),
            if (Preferences.getLoggedStatus(requireActivity())) LayoutManagerType.LINEAR_LAYOUT_MANAGER else LayoutManagerType.GRID_LAYOUT_MANAGER
        ) { menu ->
            val intent = Intent(requireActivity(), DetailMenuActivity::class.java)
            intent.putExtra(DetailMenuActivity.EXTRA_ITEM, menu) // Ganti "MENU_ID" dengan key yang sesuai
            requireActivity().startActivity(intent)
        }
    }


    fun switchLayoutManager(layoutManagerType: LayoutManagerType) {
        val layoutManager = when (layoutManagerType) {
            LayoutManagerType.LINEAR_LAYOUT_MANAGER -> LinearLayoutManager(requireContext())
            LayoutManagerType.GRID_LAYOUT_MANAGER -> GridLayoutManager(requireContext(), 2) // Sesuaikan jumlah kolom di sini
        }
        binding.rvProductList.layoutManager = layoutManager
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
        auth = FirebaseAuth.getInstance()
        getCategoryData() // Fetch category data when fragment is created
        getMenuData(null) // Fetch menu data with null slug initially
        Toast.makeText(requireContext(), "${Preferences.getLoggedStatus(requireActivity())}", Toast.LENGTH_SHORT).show()
        binding.btnSwitch.setOnClickListener {
            menuAdapter.notifyDataSetChanged()
            Preferences.setLoggedStatus(requireActivity(), !Preferences.getLoggedStatus(requireActivity()))

            if (Preferences.getLoggedStatus(requireActivity())) {
                switchLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER)
            } else {
                switchLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER)
            }
        }

        binding.layoutHeader.ivProfileHeaderHome.setOnClickListener {
            if (!userIsLoggedIn()) {
                // If not logged in, navigate to home fragment
                startActivity(Intent(requireActivity(),LoginActivity::class.java))
            } else {
                // If logged in, setup bottom navigation
                binding.layoutHeader.ivProfileHeaderHome.isEnabled = false
            }
        }



        observeListMode()
    }

    private fun observeListMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            switchLayoutManager(if (isUsingGridMode) LayoutManagerType.GRID_LAYOUT_MANAGER else LayoutManagerType.LINEAR_LAYOUT_MANAGER)
            getMenuData(null)
        }
    }

    private fun getCategoryData() {
        viewModel.getCategories().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = { categories ->
                    categories.payload?.let { bindCategoryList(it) }
                },
                doOnError = { error ->
                    Toast.makeText(
                        requireContext(),
                        "$error ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun getMenuData(slug: String?) {
        viewModel.getMenus(slug).observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnSuccess = { menus ->
                    bindMenuList(menus.payload.orEmpty())
                },
                doOnError = { error ->
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch menu list: ${error.message}",
                        Toast.LENGTH_SHORT
                    ).show()
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

    private fun userIsLoggedIn(): Boolean {
        // Check if the current user is authenticated
        return auth.currentUser != null
    }

    fun navigateToTabProfile() {
        val navController = requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
//        binding.navView.selectedItemId = R.id.menu_tab_profile
        navController.navigate(R.id.menu_tab_profile)
    }

}
