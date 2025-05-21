package com.example.shoppingcart.ui.fragment.categoryscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shoppingcart.databinding.FragmentTopSaleBinding


class TopSaleFragment : Fragment() {
    private lateinit var binding: FragmentTopSaleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTopSaleBinding.inflate(inflater, container, false)

        return binding.root
    }


}