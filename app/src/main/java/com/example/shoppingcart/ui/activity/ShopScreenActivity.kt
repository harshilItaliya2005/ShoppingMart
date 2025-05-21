package com.example.shoppingcart.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.shoppingcart.R
import com.example.shoppingcart.databinding.ActivityShopScreenBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import nl.joery.animatedbottombar.AnimatedBottomBar

@Suppress("DEPRECATION")
class ShopScreenActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityShopScreenBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newIndex) {
                    0 -> navController.navigate(R.id.homeFragment)
                    1 -> navController.navigate(R.id.categoryFragment)
                    2 -> navController.navigate(R.id.accountFragment)
                    3 -> navController.navigate(R.id.cartFragment)
                }
            }
        })

    }
    fun selectBottomBarTabById(tabId: Int) {
        binding.bottomBar.selectTabById(tabId)
    }

}