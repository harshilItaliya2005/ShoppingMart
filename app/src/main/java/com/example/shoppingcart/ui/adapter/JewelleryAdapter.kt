package com.example.shoppingcart.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingcart.R
import com.example.shoppingcart.data.model.product.ProductData
import com.example.shoppingcart.databinding.ItemProductBinding


class JewelleryAdapter( private var products: List<ProductData>): RecyclerView.Adapter<JewelleryAdapter.JewelleryViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): JewelleryViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JewelleryViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: JewelleryViewHolder,
        position: Int,
    ) {
        val productData = products[position]
        if (productData.category == "jewelery") {
            with(holder.binding) {
                Glide.with(holder.itemView.context)
                    .load(productData.image)
                    .useAnimationPool(true)
                    .into(productImg)
                productName.text = productData.title
                textPrice.text = "${productData.price}"
                mainPrice.text = "${productData.price + 200}"
                mainPrice.paintFlags = mainPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                rating.text = "${productData.rating.rate}"
            }
            val anims = listOf(
                R.anim.slide_in_right,
                R.anim.fade_in_scale,
                R.anim.bounce_in_left
            )

            val animation = AnimationUtils.loadAnimation(holder.itemView.context, anims.random())
            holder.itemView.startAnimation(animation)
        }
    }


    override fun getItemCount(): Int {
        return products.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newProducts: List<ProductData>) {
        products = newProducts.filter { it.category == "jewelery" }
        notifyDataSetChanged()
    }

    inner class JewelleryViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}