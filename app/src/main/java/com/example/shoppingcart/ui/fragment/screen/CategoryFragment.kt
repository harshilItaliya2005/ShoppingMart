package com.example.shoppingcart.ui.fragment.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.data.repository.ShopRepository
import com.example.shoppingcart.databinding.FragmentCategoryBinding
import com.example.shoppingcart.ui.adapter.ElectronicsAdapter
import com.example.shoppingcart.ui.adapter.JewelleryAdapter
import com.example.shoppingcart.ui.adapter.MensAdapter
import com.example.shoppingcart.ui.adapter.WomenAdapter
import com.example.shoppingcart.ui.viewmodel.CategoryViewModel
import com.example.shoppingcart.ui.viewmodel.CategoryViewModelFactory
import kotlinx.coroutines.launch


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var mensAdapter: MensAdapter
    private lateinit var womenAdapter: WomenAdapter
    private lateinit var jewelleryAdapter: JewelleryAdapter
    private lateinit var electronicsAdapter: ElectronicsAdapter

    private val repo = ShopRepository()
    private val viewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        binding.progressBar2.visibility = View.VISIBLE

        mensAdapter = MensAdapter(emptyList())
        binding.mensRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.mensRc.adapter = mensAdapter

        womenAdapter = WomenAdapter(emptyList())
        binding.womenRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.womenRc.adapter = womenAdapter

        jewelleryAdapter = JewelleryAdapter(emptyList())
        binding.jewelleryRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.jewelleryRc.adapter = jewelleryAdapter

        electronicsAdapter = ElectronicsAdapter(emptyList())
        binding.electronicsRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.electronicsRc.adapter = electronicsAdapter

        viewModel.fetchProducts()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.products.collect { productList ->
                val menProducts = productList.filter { it.category.equals("men's clothing", true) }
                val womenProducts = productList.filter { it.category.equals("women's clothing", true) }
                val jewelleryProducts = productList.filter { it.category.equals("jewelery", true) }
                val electronicsProducts = productList.filter { it.category.equals("electronics", true) }

                mensAdapter.updateData(menProducts)
                womenAdapter.updateData(womenProducts)
                jewelleryAdapter.updateData(jewelleryProducts)
                electronicsAdapter.updateData(electronicsProducts)

                binding.mensCategory.isVisible = menProducts.isNotEmpty()
                binding.womenCategory.isVisible = womenProducts.isNotEmpty()
                binding.jewelleryCategory.isVisible = jewelleryProducts.isNotEmpty()
                binding.electronicsCategory.isVisible = electronicsProducts.isNotEmpty()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categories.collect { categoryList ->
                binding.mensCategory.text =
                    categoryList.find { it.equals("men's clothing", true) } ?: "Men's Clothing"
                binding.womenCategory.text =
                    categoryList.find { it.equals("women's clothing", true) } ?: "Women's Clothing"
                binding.jewelleryCategory.text =
                    categoryList.find { it.equals("jewelery", true) } ?: "Jewellery"
                binding.electronicsCategory.text =
                    categoryList.find { it.equals("electronics", true) } ?: "Electronics"
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collect { loading ->
                binding.progressBar2.isVisible = loading
            }
        }
        return binding.root
    }
}