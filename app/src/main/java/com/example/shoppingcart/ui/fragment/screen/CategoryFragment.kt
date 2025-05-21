package com.example.shoppingcart.ui.fragment.screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var mensAdapter: MensAdapter
    private lateinit var womenAdapter: WomenAdapter
    private lateinit var jewelleryAdapter: JewelleryAdapter
    private lateinit var electronicsAdapter: ElectronicsAdapter
    private val handler = Handler(Looper.getMainLooper())

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
    ): View? {
        handler.postDelayed({
            if (binding.progressBar2.isVisible) {
                binding.progressBar2.visibility = View.GONE
                Log.d("HomeFragment", "Timeout reached: No products loaded.")
            }
        }, 2000)
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

        binding.progressBar2.visibility = View.VISIBLE
        viewModel.fetchProducts()


        lifecycleScope.launchWhenStarted {
            viewModel.products.collect { productList ->
                val menProducts = productList.filter { it.category.equals("men's clothing", true) }
                val womenProducts = productList.filter { it.category.equals("women's clothing", true) }
                val jewelleryProducts = productList.filter { it.category.equals("jewelery", true) }
                val electronicsProducts = productList.filter { it.category.equals("electronics", true) }

                mensAdapter.updateData(menProducts)
                womenAdapter.updateData(womenProducts)
                jewelleryAdapter.updateData(jewelleryProducts)
                electronicsAdapter.updateData(electronicsProducts)

                binding.progressBar2.visibility = View.GONE
            }
        }


        return binding.root
    }


}