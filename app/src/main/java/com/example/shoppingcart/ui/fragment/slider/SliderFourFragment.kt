package com.example.shoppingcart.ui.fragment.slider

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoppingcart.databinding.FragmentSliderFourBinding
import com.example.shoppingcart.ui.activity.ShopScreenActivity

class SliderFourFragment : Fragment() {
    lateinit var binding: FragmentSliderFourBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSliderFourBinding.inflate(inflater, container, false)
        binding.btnStart.setOnClickListener {
            startActivity(Intent(requireContext(), ShopScreenActivity::class.java))
            requireActivity().finish()
        }
        return binding.root
    }

}