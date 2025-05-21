package com.example.shoppingcart.ui.fragment.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        viewModel.fetchProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        mensAdapter = MensAdapter(emptyList())
        binding.mensRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.mensRc.adapter = mensAdapter

        womenAdapter = WomenAdapter(emptyList())
        binding.womenRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.womenRc.adapter = womenAdapter

        jewelleryAdapter = JewelleryAdapter(emptyList())
        binding.jewelleryRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.jewelleryRc.adapter = jewelleryAdapter

        electronicsAdapter = ElectronicsAdapter(emptyList())
        binding.electronicsRc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        binding.electronicsRc.adapter = electronicsAdapter

        @Suppress("DEPRECATION")
        lifecycleScope.launchWhenStarted {
            viewModel.products.collect { productList ->
                mensAdapter.updateData(productList)
                jewelleryAdapter.updateData(productList)
                womenAdapter.updateData(productList)
                electronicsAdapter.updateData(productList)
            }
        }

        return binding.root
    }


}