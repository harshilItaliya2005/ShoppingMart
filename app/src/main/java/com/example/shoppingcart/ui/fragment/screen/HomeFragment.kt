@file:Suppress("DEPRECATION")

package com.example.shoppingcart.ui.fragment.screen

import BannerAdapter
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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingcart.R
import com.example.shoppingcart.data.model.Banner
import com.example.shoppingcart.data.repository.ShopRepository
import com.example.shoppingcart.databinding.FragmentHomeBinding
import com.example.shoppingcart.ui.activity.ShopScreenActivity
import com.example.shoppingcart.ui.adapter.CategoryAdapter
import com.example.shoppingcart.ui.adapter.ProductAdapter
import com.example.shoppingcart.ui.viewmodel.ShopScreenViewModel
import com.example.shoppingcart.ui.viewmodel.ShopScreenViewModelFactory


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private val handler = Handler(Looper.getMainLooper())
    private var bannerIndex = 0
    val bannerList = listOf(
        Banner(R.drawable.banner1),
        Banner(R.drawable.banner2),
        Banner(R.drawable.banner3),
        Banner(R.drawable.banner4),
        Banner(R.drawable.banner5)
    )

    private val repository = ShopRepository()
    private val autoScrollRunnable = object : Runnable {
        override fun run() {
            val viewPager = binding.bannerViewPager
            if (bannerList.isNotEmpty()) {
                bannerIndex = (bannerIndex + 1) % bannerList.size
                viewPager.setCurrentItem(bannerIndex, true)
                handler.postDelayed(this, 2000)
            }
        }
    }

    private lateinit var adapter: CategoryAdapter
    private val viewModel: ShopScreenViewModel by viewModels {
        ShopScreenViewModelFactory(repository)
    }
    private lateinit var productAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        navController = findNavController()

        binding.progressBar.visibility = View.VISIBLE

        handler.postDelayed({
            if (binding.progressBar.isVisible) {
                binding.progressBar.visibility = View.GONE
                Log.d("HomeFragment", "Timeout reached: No products loaded.")
            }
        }, 2000)
        val bannerAdapter = BannerAdapter(bannerList)
        val viewPager = binding.bannerViewPager
        val dot = binding.dotsIndicator

        viewPager.adapter = bannerAdapter
        dot.attachTo(viewPager)

        handler.postDelayed(autoScrollRunnable, 2000)

        adapter = CategoryAdapter(emptyList())
        binding.rcCategories.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rcCategories.adapter = adapter

        val layoutManager = GridLayoutManager(context,2)
        productAdapter = ProductAdapter(emptyList())
        binding.rcProduct.layoutManager = layoutManager
        binding.rcProduct.itemAnimator = DefaultItemAnimator()
        binding.rcProduct.adapter = productAdapter

        binding.progressBar.visibility = View.VISIBLE
        viewModel.fetchProductCategoriesUriAndName()

        @Suppress("DEPRECATION")
        lifecycleScope.launchWhenStarted {
            viewModel.products.collect { productList ->
                if (productList.isNotEmpty()) {
                    val uniqueImageUrls = productList.map { it.category.trim() }.distinct()
                    adapter.updateData(uniqueImageUrls)
                    productAdapter.updateData(productList)
                    binding.progressBar.visibility = View.GONE
                    Log.d("HomeFragment", "Products loaded: hiding progress bar.")
                }

            }
        }


        binding.cardCategory.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_categoryFragment)
            (activity as? ShopScreenActivity)?.selectBottomBarTabById(R.id.category)
        }

        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(autoScrollRunnable)
    }

}