package com.example.shoppingcart.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppingcart.data.model.product.ProductData
import com.example.shoppingcart.databinding.ItemProductBinding

class ProductAdapter(
    private var products: List<ProductData>,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productData = products[position]
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

        holder.itemView.apply {
            alpha = 0f
            translationX = when (position % 4) {
                0 -> -200f
                1 -> 200f
                else -> 0f
            }
            translationY = when (position % 4) {
                2 -> -200f
                3 -> 200f
                else -> 0f
            }

            animate()
                .translationX(0f)
                .translationY(0f)
                .alpha(1f)
                .setDuration(500)
                .start()
        }
    }

    override fun getItemCount(): Int = products.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newProducts: List<ProductData>) {
        products = newProducts
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)
}
