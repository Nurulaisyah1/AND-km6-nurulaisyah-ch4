package com.foodapps.presentation.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.foodapps.data.datasource.Menu.DummyMenuDataSource
import com.foodapps.data.datasource.category.DummyCategoryDataSource
import com.foodapps.data.model.Category
import com.foodapps.data.model.Menu
import com.foodapps.data.repository.CategoryRepository
import com.foodapps.data.repository.CategoryRepositoryImpl
import com.foodapps.data.repository.MenuRepository
import com.foodapps.data.repository.MenuRepositoryImpl
import com.foodapps.databinding.FragmentHomeBinding
import com.foodapps.presentation.DetailMenuActivity
import com.foodapps.presentation.home.adapter.CategoryListAdapter
import com.foodapps.presentation.home.adapter.MenuListAdapter
import com.foodapps.utils.GenericViewModelFactory
import com.foodapps.utils.GridSpacingItemDecoration
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken as GsonTypeToken

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels {
        val menuDataSource = DummyMenuDataSource()
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }

    private val categoryAdapter: CategoryListAdapter by lazy {
        CategoryListAdapter {

        }
    }

    private val menuAdapter: MenuListAdapter by lazy {
        MenuListAdapter { menu ->
            addToCart(menu)
            DetailMenuActivity.startActivity(requireContext(), menu)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindCategoryList(viewModel.getCategories())
        bindMenuList(viewModel.getMenus())
    }

    private fun bindCategoryList(data: List<Category>) {
        binding.rvCategory.adapter = categoryAdapter
        categoryAdapter.submitData(data)
    }

    private fun bindMenuList(data: List<Menu>) {
        val itemDecoration = GridSpacingItemDecoration(2, 12, true)
        binding.rvMenuList.apply {
            adapter = menuAdapter
            addItemDecoration(itemDecoration)
        }
        menuAdapter.submitData(data)
    }

    private fun addToCart(item: Menu) {
        // Tambahkan logika untuk menambahkan item ke keranjang, menggunakan SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("Cart", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Mendapatkan daftar item yang sudah ada di keranjang
        val json = sharedPreferences.getString("cart_items", null)
        val itemList: MutableList<Menu> = if (json != null) {
            val type = object : TypeToken<List<Menu>>() {}.type
            Gson().fromJson(json, type)
        } else {
            ArrayList()
        }

        // Menambahkan item baru ke daftar
        itemList.add(item)

        // Menyimpan daftar item yang diperbarui ke SharedPreferences
        val updatedJson = Gson().toJson(itemList)
        editor.putString("cart_items", updatedJson)
        editor.apply()

        // Tampilkan pesan bahwa item telah ditambahkan ke keranjang
        Toast.makeText(requireContext(), "Item ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
    }
}
