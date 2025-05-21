package com.example.shoppingcart.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingcart.databinding.ActivitySliderScreenBinding
import com.example.shoppingcart.ui.adapter.SliderPagerAdapter
import com.example.shoppingcart.ui.fragment.slider.SliderFourFragment
import com.example.shoppingcart.ui.fragment.slider.SliderOneFragment
import com.example.shoppingcart.ui.fragment.slider.SliderThreeFragment
import com.example.shoppingcart.ui.fragment.slider.SliderTwoFragment

class SliderScreenActivity : AppCompatActivity() {

    val binding by lazy {
        ActivitySliderScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val fragments = listOf(
            SliderOneFragment(),
            SliderTwoFragment(),
            SliderThreeFragment(),
            SliderFourFragment()
        )
        binding.viewPager.adapter = SliderPagerAdapter(this, fragments)
        binding.dotsIndicator.setViewPager2(binding.viewPager)

    }
}